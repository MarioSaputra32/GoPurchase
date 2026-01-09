package com.pab.gopurchase.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.pab.gopurchase.R
import com.pab.gopurchase.models.Category
import com.pab.gopurchase.models.Product
import java.text.NumberFormat
import java.util.Locale

class AllProductAdapter(
    private val categories: List<Category>,
    private val products: List<Product>,
    private val onProductClick: (Product) -> Unit,
    private val onAddToCartClick: (Product) -> Unit,
    private val onFavoriteClick: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CATEGORY = 0
        private const val VIEW_TYPE_PRODUCT = 1
    }

    private var selectedCategoryId = "all"
    private val displayedProducts = mutableListOf<Product>()

    init {
        displayedProducts.addAll(products)
    }

    // ================= CATEGORY VIEW =================
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rvCategories: RecyclerView = itemView.findViewById(R.id.rvCategories)

        init {
            rvCategories.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            rvCategories.adapter = CategoryAdapter(categories) { category ->
                selectedCategoryId = category.id
                filterProducts()
            }
        }
    }

    // ================= PRODUCT VIEW =================
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProduct: ImageView = itemView.findViewById(R.id.productImage)
        val tvName: TextView = itemView.findViewById(R.id.productName)
        val tvPrice: TextView = itemView.findViewById(R.id.productPrice)
        val tvRating: TextView = itemView.findViewById(R.id.productRating)
        val tvReviews: TextView = itemView.findViewById(R.id.productReviews)
        val btnAddToCart: MaterialButton = itemView.findViewById(R.id.btnAddToCart)
        val ivFavorite: ImageView = itemView.findViewById(R.id.btnFavorite)

        fun updateFavorite(isFavorite: Boolean) {
            ivFavorite.setColorFilter(
                ContextCompat.getColor(
                    itemView.context,
                    if (isFavorite) R.color.red else R.color.gray
                )
            )
        }
    }

    // ================= CATEGORY ADAPTER =================
    inner class CategoryAdapter(
        private val categories: List<Category>,
        private val onCategoryClick: (Category) -> Unit
    ) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

        private var selectedPosition = 0

        inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView = itemView.findViewById(R.id.tvCategoryName)

            init {
                itemView.setOnClickListener {
                    val pos = bindingAdapterPosition
                    if (pos == RecyclerView.NO_POSITION) return@setOnClickListener

                    val prev = selectedPosition
                    selectedPosition = pos
                    notifyItemChanged(prev)
                    notifyItemChanged(selectedPosition)

                    onCategoryClick(categories[pos])
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

    // ================= MAIN ADAPTER =================
    override fun getItemViewType(position: Int): Int =
        if (position == 0) VIEW_TYPE_CATEGORY else VIEW_TYPE_PRODUCT

    override fun getItemCount(): Int = displayedProducts.size + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_CATEGORY) {
            CategoryViewHolder(inflater.inflate(R.layout.item_container, parent, false))
        } else {
            ProductViewHolder(inflater.inflate(R.layout.item_product_card, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductViewHolder) {
            val product = displayedProducts[position - 1]
            bindProduct(holder, product)
        }
    }

    // ================= BIND PRODUCT =================
    private fun bindProduct(holder: ProductViewHolder, product: Product) {
        holder.tvName.text = product.name
        holder.tvPrice.text = "Rp ${
            NumberFormat.getInstance(Locale("in", "ID")).format(product.price)
        }"
        holder.tvRating.text = product.rating.toString()
        holder.tvReviews.text = "(${product.reviews})"

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .into(holder.ivProduct)

        holder.updateFavorite(product.isFavorite)

        holder.btnAddToCart.isEnabled = product.stock > 0
        holder.btnAddToCart.text =
            if (product.stock > 0) "Add to Cart" else "Stok Habis"

        holder.btnAddToCart.setOnClickListener {
            onAddToCartClick(product)
        }

        holder.itemView.setOnClickListener {
            onProductClick(product)
        }

        holder.ivFavorite.setOnClickListener {
            product.isFavorite = !product.isFavorite
            holder.updateFavorite(product.isFavorite)
            onFavoriteClick(product)
        }
    }

    // ================= FILTER =================
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
