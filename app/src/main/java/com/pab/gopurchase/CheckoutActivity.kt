package com.pab.gopurchase

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.pab.gopurchase.adapters.CheckoutAdapter
import com.pab.gopurchase.models.*
import com.pab.gopurchase.utils.UserPreferences
import java.text.NumberFormat
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {

    // =========================
    // VIEWS
    // =========================
    private lateinit var toolbar: MaterialToolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnPlaceOrder: MaterialButton
    private lateinit var paymentMethodGroup: RadioGroup

    private lateinit var subtotalText: TextView
    private lateinit var shippingText: TextView
    private lateinit var taxText: TextView
    private lateinit var totalText: TextView

    // 🔥 ADDRESS VIEW
    private lateinit var addressName: TextView
    private lateinit var addressDetail: TextView
    private lateinit var addressPhone: TextView

    // =========================
    // DATA
    // =========================
    private val checkoutItems = mutableListOf<CartItem>()
    private val shippingCost = 15000.0
    private val taxRate = 0.1
    private var totalPayment = 0.0

    private var selectedPaymentMethod: PaymentMethod? = null
    private lateinit var userPreferences: UserPreferences

    // =========================
    // LIFECYCLE
    // =========================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        userPreferences = UserPreferences(this)

        initViews()
        setupToolbar()
        loadUserAddress()          // 🔥 AMBIL DATA USER
        loadCheckoutItems()
        setupRecyclerView()
        calculateSummary()
        setupPaymentListener()
        setupListeners()
    }

    // =========================
    // INIT
    // =========================
    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.rvCheckoutItems)
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)

        subtotalText = findViewById(R.id.subtotalText)
        shippingText = findViewById(R.id.shippingText)
        taxText = findViewById(R.id.taxText)
        totalText = findViewById(R.id.totalText)

        paymentMethodGroup = findViewById(R.id.paymentMethodGroup)

        // 🔥 ADDRESS
        addressName = findViewById(R.id.addressName)
        addressDetail = findViewById(R.id.addressDetail)
        addressPhone = findViewById(R.id.addressPhone)
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener { finish() }
    }

    // =========================
    // LOAD USER ADDRESS
    // =========================
    private fun loadUserAddress() {
        val user = userPreferences.getUser()

        if (user != null) {
            addressName.text = user.name
            addressDetail.text = user.address
            addressPhone.text = user.phone
        } else {
            addressName.text = "-"
            addressDetail.text = "-"
            addressPhone.text = "-"
        }
    }

    // =========================
    // LOAD ITEMS
    // =========================
    private fun loadCheckoutItems() {
        val fromBuyNow =
            intent.getParcelableArrayListExtra<CartItem>("CART_ITEMS")

        checkoutItems.clear() // 🔥 WAJIB

        if (!fromBuyNow.isNullOrEmpty()) {
            checkoutItems.addAll(fromBuyNow)
        } else {
            checkoutItems.addAll(ProductData.cartItems)
        }
    }


    // =========================
    // RECYCLER VIEW
    // =========================
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CheckoutAdapter(checkoutItems)
    }

    // =========================
    // PAYMENT METHOD
    // =========================
    private fun setupPaymentListener() {
        paymentMethodGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)

            selectedPaymentMethod = when (radioButton.text.toString()) {
                "Transfer Bank" -> PaymentMethod.TRANSFER
                "E-Wallet" -> PaymentMethod.E_WALLET
                else -> PaymentMethod.CASH_ON_DELIVERY
            }
        }
    }

    // =========================
    // CALCULATE SUMMARY
    // =========================
    private fun calculateSummary() {
        val subtotal = checkoutItems.sumOf { it.getTotalPrice() }
        val tax = subtotal * taxRate
        val total = subtotal + shippingCost + tax

        totalPayment = total

        subtotalText.text = formatRupiah(subtotal)
        shippingText.text = formatRupiah(shippingCost)
        taxText.text = formatRupiah(tax)
        totalText.text = formatRupiah(total)
    }

    // =========================
    // BUTTON
    // =========================
    private fun setupListeners() {
        btnPlaceOrder.setOnClickListener {

            if (selectedPaymentMethod == null) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Silakan pilih metode pembayaran",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            showConfirmDialog()
        }
    }

    // =========================
    // CONFIRM + STOCK CHECK
    // =========================
    private fun showConfirmDialog() {

        for (item in checkoutItems) {
            val product = ProductData.products.find { it.id == item.product.id }
            if (product == null || product.stock < item.quantity) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Stok ${item.product.name} tidak mencukupi",
                    Snackbar.LENGTH_LONG
                ).show()
                return
            }
        }

        MaterialAlertDialogBuilder(this)
            .setTitle("Konfirmasi Pesanan")
            .setMessage(
                "Metode Pembayaran: ${selectedPaymentMethod!!.name}\n" +
                        "Total: ${formatRupiah(totalPayment)}"
            )
            .setPositiveButton("Confirm") { _, _ ->
                processOrder()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // =========================
    // PROCESS ORDER
    // =========================
    private fun processOrder() {

        val user = userPreferences.getUser()
            ?: run {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "User tidak ditemukan",
                    Snackbar.LENGTH_LONG
                ).show()
                return
            }

        val order = ProductData.createOrder(
            checkoutItems,
            user,
            selectedPaymentMethod!!
        )

        // Kurangi stok
        for (item in checkoutItems) {
            val product = ProductData.products.find { it.id == item.product.id }
            product?.let {
                it.stock -= item.quantity
                if (it.stock < 0) it.stock = 0
            }
        }

        // Clear cart jika bukan Buy Now
        if (intent.getParcelableArrayListExtra<CartItem>("CART_ITEMS") == null) {
            ProductData.clearCart()
        }

        val intent = Intent(this, PaymentSuccessActivity::class.java)
        intent.putExtra("ORDER_ID", order.id)
        intent.putExtra("TOTAL_AMOUNT", order.totalAmount)
        intent.putExtra("PAYMENT_METHOD", selectedPaymentMethod!!.name)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // =========================
    // FORMAT RUPIAH
    // =========================
    private fun formatRupiah(value: Double): String {
        val localeID = Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(localeID)
        return formatter.format(value).replace(",00", "")
    }
}
