package com.pab.gopurchase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EditProfileActivity : AppCompatActivity() {
    
    private lateinit var toolbar: MaterialToolbar
    private lateinit var nameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var phoneInputLayout: TextInputLayout
    
    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    
    private lateinit var btnSave: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        
        initViews()
        setupToolbar()
        loadUserData()
        setupListeners()
    }
    
    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        nameInputLayout = findViewById(R.id.nameInputLayout)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        phoneInputLayout = findViewById(R.id.phoneInputLayout)
        
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)
        
        btnSave = findViewById(R.id.btnSave)
    }
    
    private fun setupToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun loadUserData() {
        nameInput.setText("John Doe")
        emailInput.setText("john.doe@example.com")
        phoneInput.setText("+1 (555) 123-4567")
    }
    
    private fun setupListeners() {
        btnSave.setOnClickListener {
            if (validateInputs()) {
                saveProfile()
            }
        }
    }
    
    private fun validateInputs(): Boolean {
        var isValid = true
        
        val name = nameInput.text.toString().trim()
        if (name.isEmpty()) {
            nameInputLayout.error = getString(R.string.error_empty_name)
            isValid = false
        } else {
            nameInputLayout.error = null
        }
        
        val email = emailInput.text.toString().trim()
        if (email.isEmpty()) {
            emailInputLayout.error = getString(R.string.error_empty_email)
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.error = getString(R.string.error_invalid_email)
            isValid = false
        } else {
            emailInputLayout.error = null
        }
        
        val phone = phoneInput.text.toString().trim()
        if (phone.isEmpty()) {
            phoneInputLayout.error = getString(R.string.error_empty_phone)
            isValid = false
        } else {
            phoneInputLayout.error = null
        }
        
        return isValid
    }
    
    private fun saveProfile() {
        Snackbar.make(
            findViewById(android.R.id.content),
            "Profile updated successfully",
            Snackbar.LENGTH_SHORT
        ).show()
        
        finish()
    }
}
