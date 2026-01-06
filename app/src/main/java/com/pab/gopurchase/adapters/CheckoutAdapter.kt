package com.pab.gopurchase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pab.gopurchase.R
import com.pab.gopurchase.models.CartItem
import java.text.NumberFormat
import java.util.Locale

class CheckoutAdapter(
    private val items: List<CartItem>
) : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.productName)
        val productQty: TextView = view.findViewById(R.id.productQty)
        val productPrice: TextView = view.findViewById(R.id.productPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_checkout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.productName.text = item.product.name
        holder.productQty.text = "Jumlah: ${item.quantity}"
        holder.productPrice.text = rupiah(item.getTotalPrice())
    }

    override fun getItemCount(): Int = items.size

    private fun rupiah(value: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        return format.format(value)
    }
}
