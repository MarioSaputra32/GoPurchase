package com.pab.gopurchase

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    
    private lateinit var nameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var phoneInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var confirmPasswordInputLayout: TextInputLayout
    
    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    
    private lateinit var btnRegister: MaterialButton
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnBack: ImageButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        
        initViews()
        setupListeners()
    }
    
    private fun initViews() {
        nameInputLayout = findViewById(R.id.nameInputLayout)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        phoneInputLayout = findViewById(R.id.phoneInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout)
        
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInputLayout)
        
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)
        btnBack = findViewById(R.id.btnBack)
    }
    
    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnRegister.setOnClickListener {
            if (validateInputs()) {
                performRegister()
            }
        }
        
        btnLogin.setOnClickListener {
            navigateToLogin()
        }
    }
    
    private fun validateInputs(): Boolean {
        var isValid = true
        
        // Validate name
        val name = nameInput.text.toString().trim()
        if (name.isEmpty()) {
            nameInputLayout.error = getString(R.string.error_empty_name)
            isValid = false
        } else {
            nameInputLayout.error = null
        }
        
        // Validate email
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
        
        // Validate phone
        val phone = phoneInput.text.toString().trim()
        if (phone.isEmpty()) {
            phoneInputLayout.error = getString(R.string.error_empty_phone)
            isValid = false
        } else {
            phoneInputLayout.error = null
        }
        
        // Validate password
        val password = passwordInput.text.toString()
        if (password.isEmpty()) {
            passwordInputLayout.error = getString(R.string.error_empty_password)
            isValid = false
        } else if (password.length < 6) {
            passwordInputLayout.error = getString(R.string.error_short_password)
            isValid = false
        } else {
            passwordInputLayout.error = null
        }
        
        // Validate confirm password
        val confirmPassword = confirmPasswordInput.text.toString()
        if (confirmPassword.isEmpty()) {
            confirmPasswordInputLayout.error = getString(R.string.error_empty_password)
            isValid = false
        } else if (password != confirmPassword) {
            confirmPasswordInputLayout.error = getString(R.string.error_password_mismatch)
            isValid = false
        } else {
            confirmPasswordInputLayout.error = null
        }
        
        return isValid
    }
    
    private fun performRegister() {
        // TODO: Implement actual registration logic
        Snackbar.make(
            findViewById(android.R.id.content),
            "Registration successful!",
            Snackbar.LENGTH_SHORT
        ).show()
        
        // Navigate to main activity
        navigateToMain()
    }
    
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
