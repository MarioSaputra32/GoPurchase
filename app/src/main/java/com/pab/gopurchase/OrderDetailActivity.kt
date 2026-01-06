package com.pab.gopurchase

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class OrderDetailActivity : AppCompatActivity() {
    
    private lateinit var toolbar: MaterialToolbar
    private lateinit var orderIdText: TextView
    private lateinit var orderDateText: TextView
    private lateinit var statusChip: Chip
    private lateinit var itemsRecyclerView: RecyclerView
    private lateinit var deliveryAddressText: TextView
    private lateinit var paymentMethodText: TextView
    private lateinit var subtotalText: TextView
    private lateinit var shippingText: TextView
    private lateinit var taxText: TextView
    private lateinit var totalText: TextView
    private lateinit var btnTrackOrder: MaterialButton
    private lateinit var btnCancelOrder: MaterialButton
    private lateinit var btnReorder: MaterialButton
    
    private var orderId = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        
        orderId = intent.getStringExtra("ORDER_ID") ?: ""
        
        initViews()
        setupToolbar()
        loadOrderData()
        displayOrderData()
        setupListeners()
    }
    
    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        orderIdText = findViewById(R.id.orderIdText)
        orderDateText = findViewById(R.id.orderDateText)
        statusChip = findViewById(R.id.statusChip)
        itemsRecyclerView = findViewById(R.id.itemsRecyclerView)
        deliveryAddressText = findViewById(R.id.deliveryAddressText)
        paymentMethodText = findViewById(R.id.paymentMethodText)
        subtotalText = findViewById(R.id.subtotalText)
        shippingText = findViewById(R.id.shippingText)
        taxText = findViewById(R.id.taxText)
        totalText = findViewById(R.id.totalText)
        btnTrackOrder = findViewById(R.id.btnTrackOrder)
        btnCancelOrder = findViewById(R.id.btnCancelOrder)
        btnReorder = findViewById(R.id.btnReorder)
    }
    
    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun loadOrderData() {
        // TODO: Load actual order from database/backend
    }
    
    private fun displayOrderData() {
        orderIdText.text = orderId
    }
    
    private fun setupListeners() {
        btnTrackOrder.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Track Order")
                .setMessage("Order tracking feature coming soon")
                .setPositiveButton("OK", null)
                .show()
        }
        
        btnCancelOrder.setOnClickListener {
            showCancelOrderDialog()
        }
        
        btnReorder.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Reorder")
                .setMessage("Items have been added to your cart")
                .setPositiveButton("OK", null)
                .show()
        }
    }
    
    private fun showCancelOrderDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Cancel Order")
            .setMessage("Are you sure you want to cancel this order?")
            .setPositiveButton("Yes, Cancel") { _, _ ->
                cancelOrder()
            }
            .setNegativeButton("No", null)
            .show()
    }
    
    private fun cancelOrder() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Order Cancelled")
            .setMessage("Your order has been cancelled successfully")
            .setPositiveButton("OK", null)
            .show()
    }
}
