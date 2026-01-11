package com.pab.gopurchase

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pab.gopurchase.adapters.CheckoutAdapter
import com.pab.gopurchase.models.Order
import com.pab.gopurchase.models.OrderStatus
import com.pab.gopurchase.models.PaymentMethod
import com.pab.gopurchase.models.ProductData
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailActivity : AppCompatActivity() {


    // VIEWS

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


    // DATA

    private var orderId: String = ""
    private var currentOrder: Order? = null

    private val shippingCost = 15000.0
    private val taxRate = 0.1

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
        toolbar.setNavigationOnClickListener { finish() }
    }


    // LOAD DATA

    private fun loadOrderData() {
        currentOrder = ProductData.orders.find { it.id == orderId }

        if (currentOrder == null) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage("Order tidak ditemukan")
                .setPositiveButton("OK") { _, _ -> finish() }
                .show()
        }
    }

    // DISPLAY DATA

    private fun displayOrderData() {
        val order = currentOrder ?: return

        orderIdText.text = order.id

        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("in", "ID"))
        orderDateText.text = sdf.format(Date(order.createdAt))

        setupStatusChip(order.status)
        paymentMethodText.text = formatPaymentMethod(order.paymentMethod)

        deliveryAddressText.text =
            "${order.userName}\n${order.userPhone}\n${order.userAddress}"

        // RecyclerView items
        itemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@OrderDetailActivity)
            isNestedScrollingEnabled = false
            adapter = CheckoutAdapter(order.items)
        }

        val subtotal = order.items.sumOf { it.getTotalPrice() }
        val tax = subtotal * taxRate
        val total = subtotal + shippingCost + tax

        subtotalText.text = formatRupiah(subtotal)
        shippingText.text = formatRupiah(shippingCost)
        taxText.text = formatRupiah(tax)
        totalText.text = formatRupiah(total)
    }

    // STATUS CHIP
    private fun setupStatusChip(status: OrderStatus) {
        when (status) {
            OrderStatus.MENUNGGU -> {
                statusChip.text = "Menunggu"
                statusChip.setChipBackgroundColorResource(R.color.md_theme_secondary_container)
            }
            OrderStatus.DIPROSES -> {
                statusChip.text = "Diproses"
                statusChip.setChipBackgroundColorResource(R.color.md_theme_primary_container)
            }
            OrderStatus.DIKIRIM -> {
                statusChip.text = "Dikirim"
                statusChip.setChipBackgroundColorResource(R.color.md_theme_tertiary_container)
            }
            OrderStatus.DIANTAR -> {
                statusChip.text = "Selesai"
                statusChip.setChipBackgroundColorResource(R.color.success_green)
            }
            OrderStatus.DIBATALKAN -> {
                statusChip.text = "Dibatalkan"
                statusChip.setChipBackgroundColorResource(R.color.md_theme_error_container)
            }
        }
    }

    private fun formatPaymentMethod(method: PaymentMethod): String {
        return when (method) {
            PaymentMethod.CASH_ON_DELIVERY -> "Cash on Delivery"
            PaymentMethod.TRANSFER -> "Transfer Bank"
            PaymentMethod.E_WALLET -> "E-Wallet"
        }
    }


    // LISTENERS

    private fun setupListeners() {

        btnTrackOrder.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Track Order")
                .setMessage("Fitur tracking akan segera tersedia")
                .setPositiveButton("OK", null)
                .show()
        }

        btnCancelOrder.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Batalkan Pesanan")
                .setMessage("Apakah Anda yakin ingin membatalkan pesanan ini?")
                .setPositiveButton("Ya") { _, _ ->
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Pesanan Dibatalkan")
                        .setMessage("Pesanan berhasil dibatalkan")
                        .setPositiveButton("OK", null)
                        .show()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }

        btnReorder.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Pesan Ulang")
                .setMessage("Item telah ditambahkan ke keranjang")
                .setPositiveButton("OK", null)
                .show()
        }
    }

    private fun formatRupiah(value: Double): String {
        return NumberFormat.getCurrencyInstance(Locale("in", "ID"))
            .format(value)
            .replace(",00", "")
    }
}
