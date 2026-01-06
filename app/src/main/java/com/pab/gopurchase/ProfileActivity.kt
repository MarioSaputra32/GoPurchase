package com.pab.gopurchase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileActivity : AppCompatActivity() {
    
    private lateinit var toolbar: MaterialToolbar
    private lateinit var cardEditProfile: MaterialCardView
    private lateinit var cardMyOrders: MaterialCardView
    private lateinit var cardNotifications: MaterialCardView
    private lateinit var cardAddresses: MaterialCardView
    private lateinit var cardPaymentMethods: MaterialCardView
    private lateinit var cardWishlist: MaterialCardView
    private lateinit var cardSettings: MaterialCardView
    private lateinit var cardHelp: MaterialCardView
    private lateinit var cardAbout: MaterialCardView
    private lateinit var cardLogout: MaterialCardView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        
        initViews()
        setupToolbar()
        setupListeners()
    }
    
    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        cardEditProfile = findViewById(R.id.cardEditProfile)
        cardMyOrders = findViewById(R.id.cardMyOrders)
        cardNotifications = findViewById(R.id.cardNotifications)
        cardAddresses = findViewById(R.id.cardAddresses)
        cardPaymentMethods = findViewById(R.id.cardPaymentMethods)
        cardWishlist = findViewById(R.id.cardWishlist)
        cardSettings = findViewById(R.id.cardSettings)
        cardHelp = findViewById(R.id.cardHelp)
        cardAbout = findViewById(R.id.cardAbout)
        cardLogout = findViewById(R.id.cardLogout)
    }
    
    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupListeners() {
        cardEditProfile.setOnClickListener {
            navigateToEditProfile()
        }
        
        cardMyOrders.setOnClickListener {
            navigateToOrderHistory()
        }
        
        cardNotifications.setOnClickListener {
            navigateToNotifications()
        }
        
        cardAddresses.setOnClickListener {
            showComingSoon("Addresses")
        }
        
        cardPaymentMethods.setOnClickListener {
            showComingSoon("Payment Methods")
        }
        
        cardWishlist.setOnClickListener {
            showComingSoon("Wishlist")
        }
        
        cardSettings.setOnClickListener {
            showComingSoon("Settings")
        }
        
        cardHelp.setOnClickListener {
            showComingSoon("Help & Support")
        }
        
        cardAbout.setOnClickListener {
            showAboutDialog()
        }
        
        cardLogout.setOnClickListener {
            showLogoutDialog()
        }
    }
    
    private fun navigateToEditProfile() {
        val intent = Intent(this, EditProfileActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToOrderHistory() {
        val intent = Intent(this, OrderHistoryActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToNotifications() {
        val intent = Intent(this, NotificationsActivity::class.java)
        startActivity(intent)
    }
    
    private fun showComingSoon(feature: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(feature)
            .setMessage("This feature is coming soon")
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showAboutDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("About GoPurchase")
            .setMessage("GoPurchase v1.0.0\n\nShop Smart, Live Better\n\nYour one-stop e-commerce solution")
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ ->
                performLogout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun performLogout() {
        // TODO: Clear user session, preferences, etc.
        
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
