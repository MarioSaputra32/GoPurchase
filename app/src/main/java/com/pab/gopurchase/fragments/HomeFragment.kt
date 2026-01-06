package com.pab.gopurchase.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.pab.gopurchase.ProductDetailActivity
import com.pab.gopurchase.R
import com.pab.gopurchase.SearchActivity
import com.pab.gopurchase.adapters.CategoryAdapter
import com.pab.gopurchase.adapters.ProductAdapter
import com.pab.gopurchase.models.Category
import com.pab.gopurchase.models.Product
import com.pab.gopurchase.models.ProductData

class HomeFragment : Fragment() {

    private lateinit var searchBar: MaterialCardView
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var featuredRecyclerView: RecyclerView
    private lateinit var specialOffersRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupListeners()
        loadData()
    }

    private fun initViews(view: View) {
        searchBar = view.findViewById(R.id.searchBar)
        categoriesRecyclerView = view.findViewById(R.id.categoriesRecyclerView)
        featuredRecyclerView = view.findViewById(R.id.featuredRecyclerView)
        specialOffersRecyclerView = view.findViewById(R.id.specialOffersRecyclerView)
    }

    private fun setupListeners() {
        searchBar.setOnClickListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }
    }

    private fun loadData() {
        setupCategories()
        setupFeaturedProducts()
        setupSpecialOffers()
    }

    private fun setupCategories() {
        val categories = listOf(
            Category("C1", "Elektronik", R.drawable.ic_category, 4),
            Category("C2", "Fashion", R.drawable.ic_fasion, 3),
            Category("C3", "Makanan", R.drawable.ic_food, 2),
            Category("C4", "Kesehatan", R.drawable.ic_health, 1),
            Category("C5", "Rumah Tangga", R.drawable.ic_home, 1)
        )

        categoriesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        categoriesRecyclerView.adapter =
            CategoryAdapter(categories) { category ->
                Snackbar.make(
                    requireView(),
                    "Kategori: ${category.name}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
    }

    private fun setupFeaturedProducts() {
        val featuredProducts = ProductData.products.take(6)
        featuredRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        featuredRecyclerView.adapter = ProductAdapter(
            products = featuredProducts,
            onProductClick = { product -> navigateToProductDetail(product.id) },
            onAddToCart = { product, _ -> handleAddToCart(product) },
            onFavoriteClick = { product, isFavorite, _ -> handleFavoriteClick(product, isFavorite) }
        )
    }

    private fun setupSpecialOffers() {
        val specialOffers = ProductData.products.sortedByDescending { it.rating }.take(10)
        specialOffersRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        specialOffersRecyclerView.adapter = ProductAdapter(
            products = specialOffers,
            onProductClick = { product -> navigateToProductDetail(product.id) },
            onAddToCart = { product, _ -> handleAddToCart(product) },
            onFavoriteClick = { product, isFavorite, _ -> handleFavoriteClick(product, isFavorite) }
        )
    }

    private fun navigateToProductDetail(productId: String) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java)
        intent.putExtra("PRODUCT_ID", productId)
        startActivity(intent)
    }

    private fun handleAddToCart(product: Product) {
        if (product.stock == 0) {
            Snackbar.make(requireView(), "Produk ${product.name} sedang habis", Snackbar.LENGTH_SHORT).show()
            return
        }

        ProductData.addToCart(product, 1)
        Snackbar.make(requireView(), "${product.name} ditambahkan ke keranjang (Qty: 1)", Snackbar.LENGTH_SHORT).show()
    }

    private fun handleFavoriteClick(product: Product, isFavorite: Boolean) {
        val message = if (isFavorite) "${product.name} ditambahkan ke favorit"
        else "${product.name} dihapus dari favorit"

        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}
