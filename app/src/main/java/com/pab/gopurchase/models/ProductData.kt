package com.pab.gopurchase.models


object ProductData {

    // ===================== PRODUCT LIST =====================
    val products = mutableListOf(

        // ===== ELEKTRONIK =====
        Product(
            "E001",
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
            "E002",
            "Laptop Ultrabook",
            "Laptop ringan dan cepat",
            8_500_000.0,
            "https://images.unsplash.com/photo-1517336714731-489689fd1ca8",
            4.8,
            98,
            "Elektronik",
            40
        ),
        Product(
            "E003",
            "Headset Bluetooth",
            "Headset nirkabel dengan suara jernih",
            250_000.0,
            "https://images.unsplash.com/photo-1613093691025-8a07cf2d1e4b",
            4.5,
            76,
            "Elektronik",
            120
        ),
        Product(
            "E004",
            "Smartwatch",
            "Jam tangan pintar dengan fitur kesehatan",
            650_000.0,
            "https://images.unsplash.com/photo-1617625802912-cde586faf331?q=80&w=1332&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            4.6,
            84,
            "Elektronik",
            75
        ),
        Product(
            "E005",
            "Power Bank 20000mAh",
            "Power bank fast charging",
            320_000.0,
            "https://images.unsplash.com/photo-1632156752398-2b2cb4e6c907?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTl8fHBvd2VyJTIwYmFua3xlbnwwfHwwfHx8MA%3D%3D",
            4.7,
            90,
            "Elektronik",
            150
        ),

        // ===== FASHION =====
        Product(
            "F001",
            "Jaket Hoodie Pria",
            "Jaket hoodie nyaman dan stylish",
            180_000.0,
            "https://images.unsplash.com/photo-1520975916090-3105956dac38",
            4.6,
            65,
            "Fashion",
            480
        ),
        Product(
            "F002",
            "Sepatu Sneakers",
            "Sepatu sneakers casual",
            450_000.0,
            "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77",
            4.7,
            88,
            "Fashion",
            300
        ),
        Product(
            "F003",
            "Tas Ransel",
            "Tas ransel modern dan kuat",
            220_000.0,
            "https://images.unsplash.com/photo-1509762774605-f07235a08f1f?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8dGFzJTIwcmFuc2VsfGVufDB8fDB8fHww",
            4.6,
            54,
            "Fashion",
            450
        ),
        Product(
            "F004",
            "Kaos Polos Premium",
            "Kaos bahan cotton combed",
            95_000.0,
            "https://images.unsplash.com/photo-1628071787776-a1d7f8ffa8f0?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8a2FvcyUyMHBvbG9zJTIwcHJlbWl1bXxlbnwwfHwwfHx8MA%3D%3D",
            4.5,
            72,
            "Fashion",
            600
        ),
        Product(
            "F005",
            "Celana Jeans",
            "Celana jeans slim fit",
            280_000.0,
            "https://images.unsplash.com/photo-1541099649105-f69ad21f3246",
            4.6,
            60,
            "Fashion",
            350
        ),

        // ===== MAKANAN =====
        Product(
            "M001",
            "Snack Coklat Premium",
            "Coklat premium dengan rasa lezat",
            35_000.0,
            "https://plus.unsplash.com/premium_photo-1667031519185-3dad7d8931cd",
            4.8,
            140,
            "Makanan",
            890
        ),
        Product(
            "M002",
            "Kopi Arabica",
            "Kopi arabica pilihan",
            75_000.0,
            "https://images.unsplash.com/photo-1509042239860-f550ce710b93",
            4.7,
            92,
            "Makanan",
            540
        ),
        Product(
            "M003",
            "Teh Hijau",
            "Teh hijau alami dan segar",
            40_000.0,
            "https://images.unsplash.com/photo-1641997825980-cbaf406765db?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTJ8fHRlaCUyMGhpamF1fGVufDB8fDB8fHww",
            4.6,
            70,
            "Makanan",
            400
        ),
        Product(
            "M004",
            "Madu Asli",
            "Madu alami 100%",
            95_000.0,
            "https://images.unsplash.com/photo-1605887249731-5a43ea34c344?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8bWFkdSUyMGFzbGl8ZW58MHx8MHx8fDA%3D",
            4.8,
            110,
            "Makanan",
            260
        ),
        Product(
            "M005",
            "Biskuit Gandum",
            "Biskuit gandum sehat",
            28_000.0,
            "https://images.unsplash.com/photo-1597170986775-ddf793ae6c9c?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NDZ8fGJpc2t1aXQlMjBnYW5kdW18ZW58MHx8MHx8fDA%3D",
            4.5,
            85,
            "Makanan",
            500
        ),

        // ===== KESEHATAN =====
        Product(
            "K001",
            "Vitamin C 500mg",
            "Vitamin untuk daya tahan tubuh",
            55_000.0,
            "data:image/jpeg;base64,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zxLRLBYi4QBAFICgGL2X+RG0HiFaMrEpnDU0gOZ9U+0B6h8x939Fpp1dy/n+TRTrOOW045KV4+6SOLfWHwXdVIveao1oS3mlXOgQG2Oke7Y0gcXZBUlUjHaznKtCO87aWkDTces72vuN8va/vYs9SpfJ5L6/wZatZyy2L6nY1lsvPmdqzyd2Zm7mbNo8x+qtS/uR819yHsPSL6qeaEBi51kBSVv13HifkvwHS+FrSxtSUYNptbvBG6lJaCzOKrp45WOilYySN1sTHtDmusbi4PiAsNKhiqUlOEZJreky7cWrM56PRdNC1zIYooWuLS4RNDLkbD6vBdamv1WpVFJtcVchaC2G3Ux8Xfmyf1KnU4ruP9K9Cbx4jVR+0786T+pOpxfcf6V6Ee7x+o1cXtO/Ok/qU9Tiu4/0r0F48fqNXF7TvzpP6k6rFdx/pXoPd4/UjVRe0fzZOqnqsX3H+leg93j9Rq4vaP5r+qdVi+4/0/wPd/GNXD7R/Nf1TqsX3H+n+B7vH6jVw+0fzX9U6rF9x/pXoPc4/UyiETTcHO1rl7nWBtcC5Ntg5KJ0MXJWcH+m32QTit5t9IZ7Q5rlqWI7j5FtKPEj0hntN5pqWJ7j5DSjxJ17PaHNNSxHcfIaUeI17PaHNNSxH+N8hpR4ka9ntDmmpYj/ABvkNKPEa9ntBRqeI7j5DSjxGvb7Q5pqWI7j5MaUeJgXR8QDxaSP02q+q4ruPkT1i4kFzf8AMJ8w0/JNVxH+N8mT1kSQR7fIMHyTVa/+N8mOsj4E+pvN/Mk/BRq2J3QfIjrFxM8Y4quqYjuPkV0lxJxBNUr9x8hpLiZxi5HmP1XWjg67qR9x7Vu8SHONtp6FjrhfSzzzJAQRdAaH0bSbm6zzw0JS0mWUmjA6Pj8fgq6pDxGmzHuyPx5jomqQ8RpsjuqLx5jomqQ8RpsjumLgeY6KdUhxY02O6YuDuY6JqkPEabHdEXB3MdE1WHiNJkd0RcDzHRNVh4jSY7ni4O5jomqw8RpMdzxcHcx0TVYeI0mO54uDuY6JqsPEaTHc8XA8x0TVYeI0mO54uB5jomqw8RpMdzw8Hcx0TVYeI0mO54uDuY6JqsPEabHc8PB3MdE1WHiNJjueLg7mOiarDxGmx3PFwdzHRNVh4jSY7ni4O5jomqw8RpMd0RcHcx0TVYeI02T3RF/FzHRNVh4jTYGiYv4uY6JqsPEabJ7qi8eY6KNUh4jTZI0ZH48x0TVIeI0mSNHR+PwTVIeI0mZtoWDj8EWEguI0mdDRZaipKAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAIAgCAID//Z",
            4.6,
            110,
            "Kesehatan",
            700
        ),
        Product(
            "K002",
            "Masker Medis",
            "Masker 3-ply",
            25_000.0,
            "https://images.unsplash.com/photo-1622631090360-ba04acd2e02f?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8bWFza2VyJTIwbWVkaXN8ZW58MHx8MHx8fDA%3D",
            4.5,
            95,
            "Kesehatan",
            1200
        ),
        Product(
            "K003",
            "Hand Sanitizer",
            "Hand sanitizer antiseptik",
            18_000.0,
            "https://images.unsplash.com/photo-1583947215259-38e31be8751f",
            4.6,
            80,
            "Kesehatan",
            800
        ),
        Product(
            "K004",
            "Termometer Digital",
            "Termometer pengukur suhu",
            65_000.0,
            "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxAPDg4PEA8QDQ0NDQ4NDQ0QDxINDg0NFREWFhUSExUYHSggGBolGxUVIT0hJSorLi8uFx8/RDMsNzQ5LisBCgoKDg0OFxAPGysmHyUrKysxNTc3LTc3LiswLTc3Ny43LCw3LiwrKzcrNysvLSs1NS0tLS8rKy0tKy0rLSsrOP/AABEIALcBEwMBIgACEQEDEQH/xAAcAAEAAgMBAQEAAAAAAAAAAAAAAgUBAwYEBwj/xAA/EAEAAgEBBAYHBgQDCQAAAAAAAQIDEQQSE1EFFCExUqEGQVNhcYGRByIjkpOxMnKzwVR00RUWJUJjotLh4v/EABoBAQACAwEAAAAAAAAAAAAAAAABAwIFBgT/xAAjEQEAAgIBAwQDAAAAAAAAAAAAAQIDEQQhcbESI1GBMUFh/9oADAMBAAIRAxEAPwD7iAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAI2vEd8xHxmIR41fFX80A2DG9HNibxzj6gkIcWvOPqcWvP+4JjXxo9/wBJYnPHKfIG0aJ2j3R85QttPvrHmD1DxW2qPF9OxrttVffPx7Qe6ckR6/7scXlE/srp2zlDXbbJToWnEn3R5ozknxRCpnap5oTmnmaFxxI8THGr4p+qmyZZiNe/lHOWiZvPfa0e6szWPI0L/jV8U/U41fFP1l8/6U9I8mHaMuCuz3zRhjHvZJ2y2GNbUi+kRuW7omO3VUV+0G01m/U8kV3KZKzO2ZI4lL2itLU/B+9EzasfOAfV+NXxT9WOPXxT9XyyPTy+7a3UskbuSuK1J228ZIy2iJrTd4Ous71Z+aMenl9InqOX715xxHW8trTkje1pu1wzO9EVtPd3QG31bj18U/U49fFP1cxsWS2XFTJrem/WLbnEtbd19WvrTzTFdNb5NbTpWtZve955VrXWZ+QOj6xXxT9Tj18U/WXM8LL38Da4r373ZPZ/LF5t5M4dp7N6tpyUiZraJ1i9JjviYntiY5SDX6S9NY8OeKztWTDvYq2itbzFZ+9aNfJUT6S4/wDHZv1J/wBFB9o19drxf5Wv9S7k5s1+W0xeerneVnvXNaIn9/19Mj0lp/j8v59f7LHY/S/hzWbZq7Vg1iLzpFcuKPF93SLR7tNf2fIYunW8sIyWifypry8tZ3E+X6Sx3i0RaJ1rMRMTHbExPdMJKD0Fzzk6M2S1p1nh2r8q3tWPKIX7Y1ncRLpcd/XSLfMRIAlmIZLaQmjeuoPnnS/QPSFtr2rLi6Srhw58tcmPHbYNn2u1I4dazWb5Jie+s9ndoj0b0P0jjzYr5OkaZsVLxa+L/ZmyYJvGk9m/S2te3Sezk7rJsurX1EHj41/HH5TrF/HH0l7OoozsIPLO039pHnCM57+Ov5tHqnYEJ6PB5uJef+aJ+Foknf5S226OQno+fUDVNrcpY3/i2dVvHda31lia5I9evxiJBCMjO+Ta3rpWfJHer662r8O0Gd5jUitJ7r6e6Y0/dngW9Wkx8U7GGIknHbwyj2x6p+idjdu6x8EYgw5t2f3jnDf+Fbti0090xrBscH0vTXbttjvibYIn4Ts+NyU2xbt60y0y4MNdgx12mt6cLZ612ibRXWv3fw4x1t97nGvZL7XGDZtZtNcNr203rziibW0jSNZ9ekdjPB2bwYf0kbY6fGqbTjrStr3pel9u377Ra9aUmkY7RTaLTH3YrNsVaxP8M9nrenZdgx7Tipa+OLUja9qz1pP4mPLrlzUred7XWJi0XjTs7Y0fXODs3gw/pM8LZvBh/SNo9Mqro2NMOL+SP2Yy5pw5JyzruWx1xzeIm3C3bWmddO2KzrHb3fd7fUtL0xzPZkivuisxEIzix+1j8sjNXx0/h016zg09X42P/UxZuNa+ThzWLRWsZLV3b5ojXt3ZjXdjXSJnv1n1ds+yNlxa68SuvPd7UpvSv8M79uc9kR8AfMftGru7Viry2Wkf993Juq+0e2u2Y/8ALU/qZHJ6tZnn3Jc1y49+/dJmJQ1SpWZ0iImdZ0jSJntVPNp9z+zuf+GbL/Lk/q3dOo/Q/YrbPsOzYbdl6Yo348N7TNpj5TbT5LxtadKx2dTgia46xPxHgAZLQAAAAAGNDRkBjRjchIBDhwhbDEtwDzW2WGm+wxye8BT5Oj/c89tgmO7s+HYv9GJpAOf4eSO60/Pt/dniZY5T8l5OGEZ2eAUvHt66VnyY4/8A0o+v/pcTssMTsccgVHHj2XmcePZea26lHI6lHIFTx49l5nHr7LzW3Uo5HUo5AqePHsvM48ey81t1KOR1KOQKnjx7Lzg48ey81t1KOR1KOQOP6W6L4+Xf3cURuVpEWx78xpMz36xzeL/dyeWD9D/6d71OOTPU45K5xUmdzDzW4mK0zMx1+3BR6Nzyw/oz/wCSy6J9Gsdclct4jJen8H3IpSk84r26z75dXGxxybseGIIxUjrEFeJhrO4qYKaQ2kCx6QAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH/9k=",
            4.7,
            60,
            "Kesehatan",
            300
        ),
        Product(
            "K005",
            "Minyak Kayu Putih",
            "Minyak kayu putih hangat",
            22_000.0,
            "https://images.unsplash.com/photo-1624454002302-36b824d7bd0a?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8TWlueWFrJTIwS2F5dSUyMFB1dGlofGVufDB8fDB8fHww",
            4.6,
            90,
            "Kesehatan",
            650
        ),

        // ===== RUMAH TANGGA =====
        Product(
            "R001",
            "Rice Cooker Digital",
            "Rice cooker multifungsi",
            550_000.0,
            "https://images.unsplash.com/photo-1599182345361-9542815e73f6",
            4.7,
            70,
            "Rumah Tangga",
            230
        ),
        Product(
            "R002",
            "Blender",
            "Blender serbaguna",
            350_000.0,
            "https://images.unsplash.com/photo-1570222094114-d054a817e56b?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8QmxlbmRlcnxlbnwwfHwwfHx8MA%3D%3D",
            4.6,
            65,
            "Rumah Tangga",
            200
        ),
        Product(
            "R003",
            "Set Alat Masak",
            "Peralatan masak anti lengket",
            480_000.0,
            "https://images.unsplash.com/photo-1584990347955-2ec0431a6e8f?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8U2V0JTIwQWxhdCUyME1hc2FrfGVufDB8fDB8fHww",
            4.7,
            55,
            "Rumah Tangga",
            150
        ),
        Product(
            "R004",
            "Dispenser Air",
            "Dispenser panas dingin",
            720_000.0,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-zKj6PQ3JCglGaKx588M5qmcjehYy8QUC9g&s",
            4.6,
            48,
            "Rumah Tangga",
            90
        ),
        Product(
            "R005",
            "Vacuum Cleaner",
            "Vacuum cleaner portable",
            620_000.0,
            "https://plus.unsplash.com/premium_photo-1664372899494-774422f7ce61?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8dmFjdXVtJTIwY2xlYW5lcnxlbnwwfHwwfHx8MA%3D%3D",
            4.7,
            52,
            "Rumah Tangga",
            110
        )
    )

    // ===================== CART =====================
    val cartItems = mutableListOf<CartItem>()

    fun addToCart(product: Product, qty: Int) {
        val existing = cartItems.find { it.product.id == product.id }
        if (existing != null) {
            existing.quantity += qty
        } else {
            cartItems.add(CartItem(product, qty))
        }
    }

    fun clearCart() = cartItems.clear()

    fun getCartTotal(): Double =
        cartItems.sumOf { it.getTotalPrice() }

    // ===================== ORDER =====================
    val orders = mutableListOf<Order>()

    fun createOrder(
        items: List<CartItem>,
        user: User,
        paymentMethod: PaymentMethod
    ): Order {

        // Subtotal (tanpa ongkir & pajak)
        val subtotal = items.sumOf { it.getTotalPrice() }

        val order = Order(
            id = System.currentTimeMillis().toString(),
            items = items.map { it.copy() }, // snapshot item
            totalAmount = subtotal,
            status = OrderStatus.PROCESSING,
            createdAt = System.currentTimeMillis(),

            // 🔥 SNAPSHOT DATA USER
            userName = user.name,
            userPhone = user.phone,
            userAddress = user.address,

            paymentMethod = paymentMethod
        )

        orders.add(order)
        return order
    }
}
