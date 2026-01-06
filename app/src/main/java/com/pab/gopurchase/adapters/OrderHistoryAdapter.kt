package com.pab.gopurchase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pab.gopurchase.R
import com.pab.gopurchase.models.Order
import java.text.NumberFormat
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Date

class OrderHistoryAdapter(
    private val orders: List<Order>,
    private val onClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderIdText: TextView = itemView.findViewById(R.id.orderIdText)
        val orderDateText: TextView = itemView.findViewById(R.id.orderDateText)
        val orderTotalText: TextView = itemView.findViewById(R.id.orderTotalText)
        val orderStatusText: TextView = itemView.findViewById(R.id.orderStatusText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_history, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        holder.orderIdText.text = order.id
        holder.orderTotalText.text = formatRupiah(order.totalAmount)
        holder.orderStatusText.text = order.status.name

        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("in", "ID"))
        holder.orderDateText.text = sdf.format(Date(order.createdAt))

        holder.itemView.setOnClickListener {
            onClick(order)
        }
    }

    override fun getItemCount(): Int = orders.size

    private fun formatRupiah(value: Double): String {
        val localeID = Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(localeID)
        return formatter.format(value).replace(",00", "")
    }
}
