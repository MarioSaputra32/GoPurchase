package com.pab.gopurchase.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// =======================
// PRODUCT
// =======================
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


// =======================
// CART ITEM
// =======================
@Parcelize
data class CartItem(
    val product: Product,
    var quantity: Int
) : Parcelable {

    fun getTotalPrice(): Double {
        return product.price * quantity
    }
}


// =======================
// ADDRESS
// =======================
@Parcelize
data class Address(
    val name: String,
    val phone: String,
    val street: String,
    val city: String
) : Parcelable


// =======================
// ENUMS
// =======================
@Parcelize
enum class OrderStatus : Parcelable {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
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


// =======================
// ORDER
// =======================
@Parcelize
data class Order(
    val id: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val status: OrderStatus,
    val createdAt: Long,
    val address: Address,
    val paymentMethod: PaymentMethod
) : Parcelable


// =======================
// DUMMY DATA + LOGIC
// =======================
object ProductData {

    // PRODUCT LIST
    val products = listOf(
        Product(
            "P001",
            "Smartphone Android X",
            "Smartphone Android dengan performa tinggi",
            3_200_000.0,
            "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9",
            4.7,
            120,
            "Elektronik",
            50
        ),
        Product(
            "P002",
            "Laptop Ultrabook",
            "Laptop ringan dan cepat",
            8_500_000.0,
            "https://images.unsplash.com/photo-1517336714731-489689fd1ca8",
            4.8,
            98,
            "Elektronik",
            stock = 50
        ),
        Product(
            "P003",
            "Headset Bluetooth",
            "Headset nirkabel dengan suara jernih",
            250_000.0,
            "https://images.unsplash.com/photo-1613093691025-8a07cf2d1e4b",
            4.5,
            76,
            "Elektronik",
            stock = 120
        ),
        Product(
            "P004",
            "Jaket Hoodie Pria",
            "Jaket hoodie nyaman dan stylish",
            180_000.0,
            "https://images.unsplash.com/photo-1520975916090-3105956dac38",
            4.6,
            65,
            "Fashion",
            stock = 480
        ),
        Product(
            "P005",
            "Sepatu Sneakers",
            "Sepatu sneakers casual",
            450_000.0,
            "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77",
            4.7,
            88,
            "Fashion",
            stock = 300
        ),
        Product(
            "P006",
            "Tas Ransel",
            "Tas ransel modern dan kuat",
            220_000.0,
            "https://images.unsplash.com/photo-1503342217505-b0a15ec3261c",
            4.6,
            54,
            "Fashion",
            stock = 450
        ),
        Product(
            "P007",
            "Snack Coklat Premium",
            "Coklat premium dengan rasa lezat",
            35_000.0,
            "https://plus.unsplash.com/premium_photo-1667031519185-3dad7d8931cd",
            4.8,
            140,
            "Makanan",
            stock = 890
        ),
        Product(
            "P008",
            "Kopi Arabica",
            "Kopi arabica pilihan",
            75_000.0,
            "https://images.unsplash.com/photo-1509042239860-f550ce710b93",
            4.7,
            92,
            "Makanan",
            stock = 540
        ),
        Product(
            "P009",
            "Vitamin C 500mg",
            "Vitamin untuk menjaga daya tahan tubuh",
            55_000.0,
            "https://images.unsplash.com/photo-1584305574647-0cc949a2bb9f",
            4.6,
            110,
            "Kesehatan",
            700
        ),
        Product(
            "P010",
            "Rice Cooker Digital",
            "Rice cooker digital multifungsi",
            550_000.0,
            "https://images.unsplash.com/photo-1599182345361-9542815e73f6",
            4.7,
            70,
            "Rumah Tangga",
            stock = 230
        )
    )


    // CART
    val cartItems = mutableListOf<CartItem>()

    fun addToCart(product: Product, qty: Int) {
        val existing = cartItems.find { it.product.id == product.id }
        if (existing != null) {
            existing.quantity += qty
        } else {
            cartItems.add(CartItem(product, qty))
        }
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun getCartTotal(): Double {
        return cartItems.sumOf { it.getTotalPrice() }
    }

    // ORDER HISTORY
    val orders = mutableListOf<Order>()

    fun createOrder(items: List<CartItem>): Order {

        // 🔥 snapshot item (biar cart di-clear order tetap ada)
        val snapshotItems = items.map { it.copy() }

        val order = Order(
            id = "ORD-${System.currentTimeMillis()}",
            items = snapshotItems,
            totalAmount = snapshotItems.sumOf { it.getTotalPrice() },
            status = OrderStatus.PENDING,
            createdAt = System.currentTimeMillis(),
            address = Address(
                name = "Mario Saputra",
                phone = "081234567890",
                street = "Jl. Merdeka No. 10",
                city = "Jakarta"
            ),
            paymentMethod = PaymentMethod.CASH_ON_DELIVERY
        )

        orders.add(0, order)
        return order
    }
}
