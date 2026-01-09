package com.pab.gopurchase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pab.gopurchase.R
import com.pab.gopurchase.models.CartItem

class OrderItemAdapter(
    private val items: List<CartItem>
) : RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
        val tvProductName: TextView = view.findViewById(R.id.tvProductName)
        val tvProductPrice: TextView = view.findViewById(R.id.tvProductPrice)
        val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
        val tvTotalPrice: TextView = view.findViewById(R.id.tvTotalPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.tvProductName.text = item.product.name
        holder.tvProductPrice.text = "Rp ${item.product.price}"
        holder.tvQuantity.text = "x${item.quantity}"
        holder.tvTotalPrice.text = "Rp ${item.getTotalPrice()}"

        Glide.with(holder.itemView.context)
            .load(item.product.imageUrl)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(holder.imgProduct)
    }

    override fun getItemCount(): Int = items.size
}
