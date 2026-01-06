package com.pab.gopurchase.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pab.gopurchase.R
import com.pab.gopurchase.EditProfileActivity
import com.pab.gopurchase.LoginActivity
import com.pab.gopurchase.NotificationsActivity
import com.pab.gopurchase.OrderHistoryActivity

class ProfileFragment : Fragment() {
    
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView
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
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initViews(view)
        loadUserData()
        setupListeners()
    }
    
    private fun initViews(view: View) {
        profileName = view.findViewById(R.id.profileName)
        profileEmail = view.findViewById(R.id.profileEmail)
        cardEditProfile = view.findViewById(R.id.cardEditProfile)
        cardMyOrders = view.findViewById(R.id.cardMyOrders)
        cardNotifications = view.findViewById(R.id.cardNotifications)
        cardAddresses = view.findViewById(R.id.cardAddresses)
        cardPaymentMethods = view.findViewById(R.id.cardPaymentMethods)
        cardWishlist = view.findViewById(R.id.cardWishlist)
        cardSettings = view.findViewById(R.id.cardSettings)
        cardHelp = view.findViewById(R.id.cardHelp)
        cardAbout = view.findViewById(R.id.cardAbout)
        cardLogout = view.findViewById(R.id.cardLogout)
    }
    
    private fun loadUserData() {
        // TODO: Load actual user data
        profileName.text = "John Doe"
        profileEmail.text = "john.doe@example.com"
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
        val intent = Intent(requireContext(), EditProfileActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToOrderHistory() {
        val intent = Intent(requireContext(), OrderHistoryActivity::class.java)
        startActivity(intent)
    }
    
    private fun navigateToNotifications() {
        val intent = Intent(requireContext(), NotificationsActivity::class.java)
        startActivity(intent)
    }
    
    private fun showComingSoon(feature: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(feature)
            .setMessage("This feature is coming soon")
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showAboutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("About GoPurchase")
            .setMessage("GoPurchase v1.0.0\n\nShop Smart, Live Better")
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ ->
                performLogout()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun performLogout() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}
