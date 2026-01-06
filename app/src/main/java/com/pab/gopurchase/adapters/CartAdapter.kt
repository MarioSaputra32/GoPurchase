package com.pab.gopurchase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.pab.gopurchase.R
import com.pab.gopurchase.models.CartItem
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onCartUpdated: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productImage: ImageView = itemView.findViewById(R.id.productImage)
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        private val quantityText: TextView = itemView.findViewById(R.id.quantity)
        private val btnIncrease: MaterialButton = itemView.findViewById(R.id.btnIncrease)
        private val btnDecrease: MaterialButton = itemView.findViewById(R.id.btnDecrease)
        private val btnDelete: MaterialButton = itemView.findViewById(R.id.btnDelete)

        fun bind(item: CartItem) {
            // Set data
            productName.text = item.product.name
            productPrice.text = formatRupiah(item.getTotalPrice())
            quantityText.text = item.quantity.toString()

            // Load image
            Glide.with(itemView.context)
                .load(item.product.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(productImage)

            // Buttons
            btnIncrease.setOnClickListener {
                item.quantity++
                quantityText.text = item.quantity.toString()
                productPrice.text = formatRupiah(item.getTotalPrice())
                onCartUpdated()
            }

            btnDecrease.setOnClickListener {
                if (item.quantity > 1) {
                    item.quantity--
                    quantityText.text = item.quantity.toString()
                    productPrice.text = formatRupiah(item.getTotalPrice())
                    onCartUpdated()
                }
            }

            btnDelete.setOnClickListener {
                cartItems.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                onCartUpdated()
            }
        }

        private fun formatRupiah(price: Double): String {
            val localeID = Locale("in", "ID")
            val formatter = NumberFormat.getCurrencyInstance(localeID)
            return formatter.format(price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size
}
