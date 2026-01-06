package com.pab.gopurchase.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.pab.gopurchase.R
import com.pab.gopurchase.models.Category
import com.pab.gopurchase.models.Product

class AllProductAdapter(
    private val categories: List<Category>,
    private val products: List<Product>,
    private val onAddToCartClick: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CATEGORY = 0
        private const val VIEW_TYPE_PRODUCT = 1
    }

    private var selectedCategoryId = "all"
    private val displayedProducts = products.toMutableList()

    // =========================
    // CATEGORY VIEW HOLDER
    // =========================
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvCategories: RecyclerView = itemView.findViewById(R.id.rvCategories)
        val categoryAdapter = CategoryAdapter(categories) { category ->
            selectedCategoryId = category.id
            filterProducts()
        }

        init {
            rvCategories.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            rvCategories.adapter = categoryAdapter
        }
    }

    // =========================
    // PRODUCT VIEW HOLDER
    // =========================
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProduct: ImageView = itemView.findViewById(R.id.productImage)
        val tvName: TextView = itemView.findViewById(R.id.productName)
        val tvPrice: TextView = itemView.findViewById(R.id.productPrice)
        val tvRating: TextView = itemView.findViewById(R.id.productRating)
        val tvReviews: TextView = itemView.findViewById(R.id.productReviews)
        val btnAddToCart: MaterialButton = itemView.findViewById(R.id.btnAddToCart)
        val btnFavorite: ImageView = itemView.findViewById(R.id.btnFavorite)
    }

    // =========================
    // INNER CATEGORY ADAPTER
    // =========================
    inner class CategoryAdapter(
        private val categories: List<Category>,
        private val onCategoryClick: (Category) -> Unit
    ) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

        private var selectedPosition = 0

        inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView = itemView.findViewById(R.id.tvCategoryName)

            init {
                itemView.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position == RecyclerView.NO_POSITION) return@setOnClickListener

                    val prev = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(prev)
                    notifyItemChanged(selectedPosition)

                    onCategoryClick(categories[position])
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
            return CategoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            val category = categories[position]
            holder.tvName.text = category.name
            if (position == selectedPosition) {
                holder.tvName.setBackgroundResource(R.drawable.bg_category_selected)
                holder.tvName.setTextColor(Color.WHITE)
            } else {
                holder.tvName.setBackgroundResource(R.drawable.bg_category_unselected)
                holder.tvName.setTextColor(Color.BLACK)
            }
        }

        override fun getItemCount(): Int = categories.size
    }

    // =========================
    // MAIN ADAPTER OVERRIDES
    // =========================
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_CATEGORY else VIEW_TYPE_PRODUCT
    }

    override fun getItemCount(): Int = 1 + displayedProducts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CATEGORY) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_container, parent, false)
            CategoryViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_card, parent, false)
            ProductViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductViewHolder) {
            val product = displayedProducts[position - 1] // position 0 = categories
            bindProduct(holder, product)
        }
    }

    // =========================
    // BIND PRODUCT
    // =========================
    private fun bindProduct(holder: ProductViewHolder, product: Product) {
        holder.tvName.text = product.name
        holder.tvPrice.text = "Rp ${product.price}"
        holder.tvRating.text = product.rating.toString()
        holder.tvReviews.text = "(${product.reviews})"

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.ivProduct)

        holder.btnAddToCart.setOnClickListener { onAddToCartClick(product) }

        holder.btnFavorite.setOnClickListener {
            product.isFavorite = !product.isFavorite
            holder.btnFavorite.setImageResource(
                if (product.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border
            )
            onFavoriteClick(product)
        }
    }

    // =========================
    // FILTER PRODUCTS
    // =========================
    private fun filterProducts() {
        displayedProducts.clear()
        if (selectedCategoryId == "all") {
            displayedProducts.addAll(products)
        } else {
            val categoryName = categories.find { it.id == selectedCategoryId }?.name
            displayedProducts.addAll(products.filter { it.category == categoryName })
        }
        notifyDataSetChanged()
    }
}
