package com.pab.gopurchase

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import java.text.NumberFormat
import java.util.Locale

class PaymentSuccessActivity : AppCompatActivity() {

    private lateinit var orderId: TextView
    private lateinit var totalAmount: TextView
    private lateinit var btnViewOrder: MaterialButton
    private lateinit var btnContinueShopping: MaterialButton

    private var orderIdValue = ""
    private var totalAmountValue = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        initViews()
        loadOrderData()
        displayOrderInfo()
        setupListeners()

        // 🔥 Handle back button properly (NO WARNING)
        onBackPressedDispatcher.addCallback(this) {
            navigateToHome()
        }
    }

    // =========================
    // INIT VIEW
    // =========================
    private fun initViews() {
        orderId = findViewById(R.id.orderId)
        totalAmount = findViewById(R.id.totalAmount)
        btnViewOrder = findViewById(R.id.btnViewOrder)
        btnContinueShopping = findViewById(R.id.btnContinueShopping)
    }

    // =========================
    // LOAD DATA
    // =========================
    private fun loadOrderData() {
        orderIdValue = intent.getStringExtra("ORDER_ID") ?: "#ORD-2026-001234"
        totalAmountValue = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)
    }

    // =========================
    // DISPLAY
    // =========================
    private fun displayOrderInfo() {
        orderId.text = orderIdValue
        totalAmount.text = formatRupiah(totalAmountValue)
    }

    // =========================
    // LISTENERS
    // =========================
    private fun setupListeners() {

        btnViewOrder.setOnClickListener {
            val intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("ORDER_ID", orderIdValue)
            startActivity(intent)
        }

        btnContinueShopping.setOnClickListener {
            navigateToHome()
        }
    }

    // =========================
    // NAVIGATION
    // =========================
    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // =========================
    // RUPIAH FORMAT
    // =========================
    private fun formatRupiah(value: Double): String {
        val localeID = Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(localeID)
        return formatter.format(value).replace(",00", "")
    }
}
