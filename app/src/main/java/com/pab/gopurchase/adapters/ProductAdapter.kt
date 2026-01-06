package com.pab.gopurchase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.pab.gopurchase.R
import com.pab.gopurchase.models.Product
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private var products: List<Product>,
    private val onProductClick: (Product) -> Unit,
    private val onAddToCart: (Product, View) -> Unit,
    private val onFavoriteClick: (Product, Boolean, View) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val productImage: ImageView = itemView.findViewById(R.id.productImage)
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        private val productRating: TextView = itemView.findViewById(R.id.productRating)
        private val productReviews: TextView = itemView.findViewById(R.id.productReviews)
        private val btnAddToCart: MaterialButton = itemView.findViewById(R.id.btnAddToCart)
        private val ivFavorite: ImageView = itemView.findViewById(R.id.btnFavorite)

        fun bind(product: Product) {

            // ==== DATA ====
            productName.text = product.name
            productPrice.text = formatRupiah(product.price)
            productRating.text = product.rating.toString()
            productReviews.text = "(${product.reviews})"

            Glide.with(itemView.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(productImage)

            // ==== STOCK LOGIC (FIX UTAMA) ====
            btnAddToCart.isEnabled = product.stock > 0
            btnAddToCart.text = if (product.stock > 0) "Tambah" else "Stok Habis"

            // ==== FAVORITE ====
            updateFavoriteIcon(product.isFavorite)

            // ==== CLICK LISTENERS ====
            itemView.setOnClickListener {
                onProductClick(product)
            }

            btnAddToCart.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION && product.stock > 0) {
                    onAddToCart(product, itemView)
                    notifyItemChanged(adapterPosition)
                }
            }

            ivFavorite.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    product.isFavorite = !product.isFavorite
                    updateFavoriteIcon(product.isFavorite)
                    onFavoriteClick(product, product.isFavorite, itemView)
                }
            }
        }

        private fun updateFavoriteIcon(isFavorite: Boolean) {
            ivFavorite.setColorFilter(
                ContextCompat.getColor(
                    itemView.context,
                    if (isFavorite) R.color.red else R.color.gray
                )
            )
        }

        private fun formatRupiah(price: Double): String {
            val localeID = Locale("in", "ID")
            val formatter = NumberFormat.getCurrencyInstance(localeID)
            return formatter.format(price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product_card, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun updateData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
