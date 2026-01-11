package com.pab.gopurchase.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val rating: Double,
    val reviews: Int,
    val category: String,
    var stock: Int,
    var isFavorite: Boolean = false
) : Parcelable



@Parcelize
data class CartItem(
    val product: Product,
    var quantity: Int
) : Parcelable {

    fun getTotalPrice(): Double {
        return product.price * quantity
    }
}

data class User(
    val name: String,
    val email: String,
    val phone: String,
    val address: String
)


@Parcelize
enum class OrderStatus : Parcelable {
    MENUNGGU,
    DIPROSES,
    DIKIRIM,
    DIANTAR,
    DIBATALKAN
}

@Parcelize
enum class PaymentMethod : Parcelable {
    CASH_ON_DELIVERY,
    TRANSFER,
    E_WALLET
}

data class Category(
    val id: String,
    val name: String,
    val icon: Int,
    val productCount: Int
)


@Parcelize
data class Order(
    val id: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val status: OrderStatus,
    val createdAt: Long,
    val userName: String,
    val userPhone: String,
    val userAddress: String,
    val paymentMethod: PaymentMethod
) : Parcelable


