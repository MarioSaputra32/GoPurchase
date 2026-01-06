package com.pab.gopurchase

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.pab.gopurchase.models.CartItem
import com.pab.gopurchase.models.Product
import com.pab.gopurchase.models.ProductData
import java.text.NumberFormat
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var productImage: ImageView
    private lateinit var productName: TextView
    private lateinit var productPrice: TextView
    private lateinit var productStock: TextView
    private lateinit var rating: TextView
    private lateinit var reviews: TextView
    private lateinit var productDescription: TextView
    private lateinit var quantity: TextView
    private lateinit var btnDecrease: MaterialButton
    private lateinit var btnIncrease: MaterialButton
    private lateinit var btnAddToCart: MaterialButton
    private lateinit var btnBuyNow: MaterialButton

    private var currentQuantity = 1
    private var currentProduct: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        initViews()
        setupToolbar()
        loadProductData()
        setupListeners()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        productImage = findViewById(R.id.productImage)
        productName = findViewById(R.id.productName)
        productPrice = findViewById(R.id.productPrice)
        productStock = findViewById(R.id.productStock)
        rating = findViewById(R.id.rating)
        reviews = findViewById(R.id.reviews)
        productDescription = findViewById(R.id.productDescription)
        quantity = findViewById(R.id.quantity)
        btnDecrease = findViewById(R.id.btnDecrease)
        btnIncrease = findViewById(R.id.btnIncrease)
        btnAddToCart = findViewById(R.id.btnAddToCart)
        btnBuyNow = findViewById(R.id.btnBuyNow)
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun loadProductData() {
        val productId = intent.getStringExtra("PRODUCT_ID") ?: return
        currentProduct = ProductData.products.find { it.id == productId }

        currentProduct?.let { product ->
            productName.text = product.name

            val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            productPrice.text = formatter.format(product.price)

            productStock.text = "Stok: ${product.stock}"
            rating.text = product.rating.toString()
            reviews.text = "(${product.reviews} ulasan)"
            productDescription.text = product.description

            Glide.with(this)
                .load(product.imageUrl)
                .placeholder(R.drawable.product_placeholder)
                .error(R.drawable.product_placeholder)
                .centerCrop()
                .into(productImage)

            updateQuantityDisplay()
        }
    }

    private fun setupListeners() {
        // Quantity buttons
        btnDecrease.setOnClickListener {
            if (currentQuantity > 1) {
                currentQuantity--
                updateQuantityDisplay()
            }
        }

        btnIncrease.setOnClickListener {
            currentProduct?.let { product ->
                if (currentQuantity < product.stock) {
                    currentQuantity++
                    updateQuantityDisplay()
                } else {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Jumlah melebihi stok tersedia",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Add to Cart
        btnAddToCart.setOnClickListener {
            currentProduct?.let { product ->
                if (product.stock <= 0) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Produk sedang habis",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val existing = ProductData.cartItems.find { it.product.id == product.id }
                val totalQty = (existing?.quantity ?: 0) + currentQuantity
                if (totalQty > product.stock) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Jumlah melebihi stok tersedia",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                ProductData.addToCart(product, currentQuantity)
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "${product.name} ditambahkan ke keranjang",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        // Buy Now
        btnBuyNow.setOnClickListener {
            currentProduct?.let { product ->
                if (product.stock <= 0) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Produk sedang habis",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val cartItem = CartItem(product, currentQuantity)
                val items = arrayListOf(cartItem)
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putParcelableArrayListExtra("CART_ITEMS", items)
                startActivity(intent)
            }
        }
    }

    private fun updateQuantityDisplay() {
        quantity.text = currentQuantity.toString()
        btnDecrease.isEnabled = currentQuantity > 1
        currentProduct?.let { product ->
            btnIncrease.isEnabled = currentQuantity < product.stock
        }
    }
}
