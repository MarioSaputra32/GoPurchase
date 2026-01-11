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

    private lateinit var toolbar: MaterialToolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnPlaceOrder: MaterialButton
    private lateinit var paymentMethodGroup: RadioGroup

    private lateinit var subtotalText: TextView
    private lateinit var shippingText: TextView
    private lateinit var taxText: TextView
    private lateinit var totalText: TextView

    private lateinit var addressName: TextView
    private lateinit var addressDetail: TextView
    private lateinit var addressPhone: TextView

    private val checkoutItems = mutableListOf<CartItem>()
    private val shippingCost = 15_000.0
    private val taxRate = 0.1
    private var totalPayment = 0.0

    private var selectedPaymentMethod: PaymentMethod? = null
    private lateinit var userPreferences: UserPreferences
    private var fromSource: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        userPreferences = UserPreferences(this)
        fromSource = intent.getStringExtra("FROM")

        initViews()
        setupToolbar()
        loadUserAddress()
        loadCheckoutItems()
        setupRecyclerView()
        calculateSummary()
        setupPaymentListener()
        setupListeners()
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.rvCheckoutItems)
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)

        subtotalText = findViewById(R.id.subtotalText)
        shippingText = findViewById(R.id.shippingText)
        taxText = findViewById(R.id.taxText)
        totalText = findViewById(R.id.totalText)

        paymentMethodGroup = findViewById(R.id.paymentMethodGroup)

        addressName = findViewById(R.id.addressName)
        addressDetail = findViewById(R.id.addressDetail)
        addressPhone = findViewById(R.id.addressPhone)
    }

    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun loadUserAddress() {
        userPreferences.getUser()?.let { user ->
            addressName.text = user.name
            addressDetail.text = user.address
            addressPhone.text = user.phone
        }
    }

    private fun loadCheckoutItems() {
        checkoutItems.clear()
        checkoutItems.addAll(
            intent.getParcelableArrayListExtra("CART_ITEMS") ?: emptyList()
        )
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CheckoutAdapter(checkoutItems)
    }

    private fun setupPaymentListener() {
        paymentMethodGroup.setOnCheckedChangeListener { group, checkedId ->
            val rb = group.findViewById<RadioButton>(checkedId)
            selectedPaymentMethod = when (rb.text.toString()) {
                "Transfer Bank" -> PaymentMethod.TRANSFER
                "E-Wallet" -> PaymentMethod.E_WALLET
                else -> PaymentMethod.CASH_ON_DELIVERY
            }
        }
    }

    private fun calculateSummary() {
        val subtotal = checkoutItems.sumOf { it.getTotalPrice() }
        val tax = subtotal * taxRate
        totalPayment = subtotal + shippingCost + tax

        subtotalText.text = formatRupiah(subtotal)
        shippingText.text = formatRupiah(shippingCost)
        taxText.text = formatRupiah(tax)
        totalText.text = formatRupiah(totalPayment)
    }

    private fun setupListeners() {
        btnPlaceOrder.setOnClickListener {
            if (selectedPaymentMethod == null) {
                Snackbar.make(it, "Pilih metode pembayaran", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            showConfirmDialog()
        }
    }

    private fun showConfirmDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Konfirmasi Pesanan")
            .setMessage(
                "Metode: ${selectedPaymentMethod!!.name}\n" +
                        "Total: ${formatRupiah(totalPayment)}"
            )
            .setPositiveButton("Confirm") { _, _ ->
                processOrder()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun processOrder() {
        val user = userPreferences.getUser() ?: return

        val order = ProductData.createOrder(
            items = checkoutItems,
            user = user,
            paymentMethod = selectedPaymentMethod!!,
            totalPayment = totalPayment
        )

        checkoutItems.forEach { item ->
            ProductData.products.find { it.id == item.product.id }?.let {
                it.stock = (it.stock - item.quantity).coerceAtLeast(0)
            }
        }

        if (fromSource == "CART") {
            ProductData.clearCart()
        }

        val intent = Intent(this, PaymentSuccessActivity::class.java)
        intent.putExtra("ORDER_ID", order.id)
        intent.putExtra("TOTAL_AMOUNT", order.totalAmount)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun formatRupiah(value: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        return formatter.format(value).replace(",00", "")
    }
}
