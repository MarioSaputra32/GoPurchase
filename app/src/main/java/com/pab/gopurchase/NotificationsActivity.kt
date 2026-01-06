package com.pab.gopurchase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NotificationsActivity : AppCompatActivity() {
    
    private lateinit var toolbar: MaterialToolbar
    private lateinit var notificationsRecyclerView: RecyclerView
    private lateinit var btnMarkAllRead: MaterialButton
    private lateinit var btnClearAll: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        
        initViews()
        setupToolbar()
        loadNotifications()
        setupListeners()
    }
    
    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        notificationsRecyclerView = findViewById(R.id.notificationsRecyclerView)
        btnMarkAllRead = findViewById(R.id.btnMarkAllRead)
        btnClearAll = findViewById(R.id.btnClearAll)
    }
    
    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun loadNotifications() {
        // TODO: Load actual notifications from database
    }
    
    private fun setupListeners() {
        btnMarkAllRead.setOnClickListener {
            markAllAsRead()
        }
        
        btnClearAll.setOnClickListener {
            showClearAllDialog()
        }
    }
    
    private fun markAllAsRead() {
        MaterialAlertDialogBuilder(this)
            .setMessage("All notifications marked as read")
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun showClearAllDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Clear All Notifications")
            .setMessage("Are you sure you want to clear all notifications?")
            .setPositiveButton("Clear") { _, _ ->
                clearAllNotifications()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun clearAllNotifications() {
        finish()
    }
}
