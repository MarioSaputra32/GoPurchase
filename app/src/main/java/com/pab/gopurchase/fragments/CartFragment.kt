package com.pab.gopurchase.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.pab.gopurchase.CheckoutActivity
import com.pab.gopurchase.R
import com.pab.gopurchase.adapters.CartAdapter
import com.pab.gopurchase.models.ProductData
import java.text.NumberFormat
import java.util.Locale

class CartFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyLayout: LinearLayout
    private lateinit var summaryLayout: LinearLayout
    private lateinit var subtotalText: TextView
    private lateinit var shippingText: TextView
    private lateinit var totalText: TextView
    private lateinit var btnCheckout: MaterialButton

    private lateinit var cartAdapter: CartAdapter
    private val shippingFee = 15_000.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.cartRecyclerView)
        emptyLayout = view.findViewById(R.id.emptyCartLayout)
        summaryLayout = view.findViewById(R.id.cartSummaryLayout)
        subtotalText = view.findViewById(R.id.subtotalText)
        shippingText = view.findViewById(R.id.shippingText)
        totalText = view.findViewById(R.id.totalText)
        btnCheckout = view.findViewById(R.id.btnCheckout)

        setupRecycler()
        updateSummary()

        btnCheckout.setOnClickListener {
            if (ProductData.cartItems.isNotEmpty()) {
                val intent = Intent(requireContext(), CheckoutActivity::class.java)
                intent.putExtra("FROM", "CART")
                intent.putParcelableArrayListExtra(
                    "CART_ITEMS",
                    ArrayList(ProductData.cartItems)
                )
                startActivity(intent)
            }
        }
    }

    private fun setupRecycler() {
        cartAdapter = CartAdapter(ProductData.cartItems) {
            updateSummary()
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = cartAdapter
    }

    private fun updateSummary() {
        val subtotal = ProductData.cartItems.sumOf { it.getTotalPrice() }
        val total = subtotal + shippingFee
        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

        subtotalText.text = formatter.format(subtotal)
        shippingText.text = formatter.format(shippingFee)
        totalText.text = formatter.format(total)

        if (ProductData.cartItems.isEmpty()) {
            recyclerView.visibility = View.GONE
            summaryLayout.visibility = View.GONE
            emptyLayout.visibility = View.VISIBLE
        } else {
            emptyLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            summaryLayout.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        cartAdapter.notifyDataSetChanged()
        updateSummary()
    }
}
