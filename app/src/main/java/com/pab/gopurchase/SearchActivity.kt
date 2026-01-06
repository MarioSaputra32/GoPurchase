package com.pab.gopurchase

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.pab.gopurchase.adapters.ProductAdapter
import com.pab.gopurchase.models.Product
import com.pab.gopurchase.models.ProductData
import android.content.Intent

class SearchActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnClear: ImageButton
    private lateinit var searchInput: TextInputEditText
    private lateinit var txtResultCount: TextView

    private lateinit var recentSearchesSection: View
    private lateinit var popularSearchesSection: View
    private lateinit var searchResultsSection: View
    private lateinit var emptyState: View

    private lateinit var recentSearchesChipGroup: ChipGroup
    private lateinit var popularSearchesChipGroup: ChipGroup
    private lateinit var searchResultsRecyclerView: RecyclerView

    private lateinit var productAdapter: ProductAdapter
    private lateinit var sharedPref: SharedPreferences

    private val PREF_NAME = "search_pref"
    private val KEY_RECENT = "recent_searches"
    private val MAX_RECENT = 5

    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    private var categoryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViews()
        setupRecyclerView()
        setupSearchInput()
        setupListeners()
        setupPopularSearches()

        categoryName = intent.getStringExtra("CATEGORY_NAME")
        if (!categoryName.isNullOrEmpty()) {
            searchInput.setText(categoryName)
            searchInput.setSelection(categoryName!!.length)
            performSearch(categoryName!!)
        } else {
            showInitialState()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        btnClear = findViewById(R.id.btnClear)
        searchInput = findViewById(R.id.searchInput)
        txtResultCount = findViewById(R.id.resultsCount)

        recentSearchesSection = findViewById(R.id.recentSearchesSection)
        popularSearchesSection = findViewById(R.id.popularSearchesSection)
        searchResultsSection = findViewById(R.id.searchResultsSection)
        emptyState = findViewById(R.id.emptyState)

        recentSearchesChipGroup = findViewById(R.id.recentSearchesChipGroup)
        popularSearchesChipGroup = findViewById(R.id.popularSearchesChipGroup)
        searchResultsRecyclerView = findViewById(R.id.searchResultsRecyclerView)

        sharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(
            products = emptyList(),
            onProductClick = { product -> navigateToProductDetail(product.id) },
            onAddToCart = { product, view ->
                // cek stok sebelum tambah ke cart
                if (product.stock <= 0) {
                    Snackbar.make(view, "Produk ${product.name} sedang habis", Snackbar.LENGTH_SHORT).show()
                    return@ProductAdapter
                }

                val existing = ProductData.cartItems.find { it.product.id == product.id }
                val totalQty = (existing?.quantity ?: 0) + 1
                if (totalQty > product.stock) {
                    Snackbar.make(view, "Jumlah melebihi stok tersedia", Snackbar.LENGTH_SHORT).show()
                    return@ProductAdapter
                }

                ProductData.addToCart(product, 1)
                Snackbar.make(view, "${product.name} ditambahkan ke keranjang", Snackbar.LENGTH_SHORT).show()
            },
            onFavoriteClick = { product, isFavorite, view ->
                val message = if (isFavorite) "${product.name} ditambahkan ke favorit"
                else "${product.name} dihapus dari favorit"
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
            }
        )
        searchResultsRecyclerView.layoutManager = GridLayoutManager(this, 2)
        searchResultsRecyclerView.adapter = productAdapter
    }

    private fun setupSearchInput() {
        searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = searchInput.text.toString().trim()
                if (query.isNotEmpty()) {
                    performSearch(query)
                    hideKeyboard()
                }
                true
            } else false
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                searchRunnable?.let { handler.removeCallbacks(it) }

                if (query.isEmpty()) {
                    btnClear.visibility = View.GONE
                    showInitialState()
                    return
                }

                btnClear.visibility = View.VISIBLE
                searchRunnable = Runnable { performSearch(query) }
                handler.postDelayed(searchRunnable!!, 400)
            }
        })
    }

    private fun setupListeners() {
        btnBack.setOnClickListener { finish() }
        btnClear.setOnClickListener { searchInput.text?.clear() }
    }

    private fun performSearch(query: String) {
        val results = ProductData.products.filter {
            it.name.contains(query, true) || it.category.contains(query, true)
        }

        recentSearchesSection.visibility = View.GONE
        popularSearchesSection.visibility = View.GONE

        if (results.isEmpty()) {
            txtResultCount.visibility = View.GONE
            searchResultsSection.visibility = View.GONE
            emptyState.visibility = View.VISIBLE
        } else {
            emptyState.visibility = View.GONE
            searchResultsSection.visibility = View.VISIBLE

            txtResultCount.visibility = View.VISIBLE
            txtResultCount.text = "Ditemukan ${results.size} produk"

            productAdapter.updateData(results)
            saveRecentSearch(query)
        }
    }

    private fun showInitialState() {
        txtResultCount.visibility = View.GONE
        searchResultsSection.visibility = View.GONE
        emptyState.visibility = View.GONE
        popularSearchesSection.visibility = View.VISIBLE
        showRecentSearches()
    }

    private fun setupPopularSearches() {
        val popularKeywords = listOf("Elektronik", "Fashion", "Laptop", "Sepatu", "Makanan")
        popularSearchesChipGroup.removeAllViews()
        popularKeywords.forEach {
            popularSearchesChipGroup.addView(createChip(it))
        }
    }

    private fun saveRecentSearch(query: String) {
        val recent = getRecentSearches().toMutableList()
        recent.remove(query)
        recent.add(0, query)
        if (recent.size > MAX_RECENT) recent.removeLast()
        sharedPref.edit().putString(KEY_RECENT, recent.joinToString("|")).apply()
    }

    private fun getRecentSearches(): List<String> {
        val data = sharedPref.getString(KEY_RECENT, "") ?: ""
        return if (data.isEmpty()) emptyList() else data.split("|")
    }

    private fun showRecentSearches() {
        val recentSearches = getRecentSearches()
        recentSearchesChipGroup.removeAllViews()

        if (recentSearches.isEmpty()) {
            recentSearchesSection.visibility = View.GONE
            return
        }

        recentSearchesSection.visibility = View.VISIBLE
        recentSearches.forEach {
            recentSearchesChipGroup.addView(createChip(it))
        }
    }

    private fun createChip(text: String): Chip {
        return Chip(this).apply {
            this.text = text
            isClickable = true
            isCheckable = false
            setOnClickListener {
                searchInput.setText(text)
                searchInput.setSelection(text.length)
                performSearch(text)
            }
        }
    }

    private fun hideKeyboard() {
        currentFocus?.let {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun navigateToProductDetail(productId: String) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra("PRODUCT_ID", productId)
        startActivity(intent)
    }
}
