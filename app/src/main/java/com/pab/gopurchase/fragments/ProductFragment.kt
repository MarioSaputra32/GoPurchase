package com.pab.gopurchase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pab.gopurchase.R
import com.pab.gopurchase.adapters.AllProductAdapter
import com.pab.gopurchase.models.Category
import com.pab.gopurchase.models.Product
import com.pab.gopurchase.models.ProductData

class ProductFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AllProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        recyclerView = view.findViewById(R.id.productsRecyclerView)
        setupAdapter()
        return view
    }

    private fun setupAdapter() {
        val categories = getCategories()
        val products = getProducts()

        adapter = AllProductAdapter(
            categories = categories,
            products = products,
            onAddToCartClick = { product ->
                if (product.stock > 0) {
                    ProductData.addToCart(product, 1)
                    product.stock--

                    adapter.notifyItemChanged(
                        products.indexOf(product) + 1
                    )

                    Snackbar.make(
                        requireView(),
                        "${product.name} ditambahkan ke keranjang",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Snackbar.make(
                        requireView(),
                        "${product.name} stok habis",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            },
            onFavoriteClick = { product ->
                val msg = if (product.isFavorite)
                    "ditambahkan ke favorit"
                else
                    "dihapus dari favorit"

                Snackbar.make(
                    requireView(),
                    "${product.name} $msg",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        )

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 2 else 1
            }
        }

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
    }

    private fun getCategories(): List<Category> {
        return listOf(
            Category("all", "Semua", R.drawable.ic_category, 0),
            Category("C1", "Elektronik", R.drawable.ic_category, 4),
            Category("C2", "Fashion", R.drawable.ic_fasion, 3),
            Category("C3", "Makanan", R.drawable.ic_food, 2),
            Category("C4", "Kesehatan", R.drawable.ic_health, 1),
            Category("C5", "Rumah Tangga", R.drawable.ic_home, 1)
        )
    }

    private fun getProducts(): List<Product> {
        return ProductData.products
    }
}

