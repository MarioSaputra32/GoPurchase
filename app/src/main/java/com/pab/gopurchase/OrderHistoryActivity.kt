package com.pab.gopurchase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.pab.gopurchase.adapters.OrderHistoryAdapter
import com.pab.gopurchase.models.ProductData

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var ordersRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        initViews()
        setupToolbar()
        setupRecyclerView()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        ordersRecyclerView = findViewById(R.id.ordersRecyclerView)
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        val orders = ProductData.orders
        val adapter = OrderHistoryAdapter(orders) { order ->
            navigateToOrderDetail(order.id)
        }

        ordersRecyclerView.layoutManager = LinearLayoutManager(this)
        ordersRecyclerView.adapter = adapter
    }

    private fun navigateToOrderDetail(orderId: String) {
        val intent = Intent(this, OrderDetailActivity::class.java)
        intent.putExtra("ORDER_ID", orderId)
        startActivity(intent)
    }
}
