package com.pab.gopurchase

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout

    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText

    private lateinit var btnLogin: MaterialButton
    private lateinit var btnForgotPassword: MaterialButton
    private lateinit var btnRegister: MaterialButton
    private lateinit var btnGoogle: MaterialButton
    private lateinit var btnFacebook: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)

        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)

        btnLogin = findViewById(R.id.btnLogin)
        btnForgotPassword = findViewById(R.id.btnForgotPassword)
        btnRegister = findViewById(R.id.btnRegister)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnFacebook = findViewById(R.id.btnFacebook)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            if (validateInputs()) {
                performLogin()
            }
        }

        btnForgotPassword.setOnClickListener {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Forgot Password - Coming Soon",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        btnRegister.setOnClickListener {
            navigateToRegister()
        }

        btnGoogle.setOnClickListener {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Google Sign-In - Coming Soon",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        btnFacebook.setOnClickListener {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Facebook Sign-In - Coming Soon",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun validateInputs(): Boolean {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        emailInputLayout.error = when {
            email.isEmpty() -> getString(R.string.error_empty_email)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> getString(R.string.error_invalid_email)
            else -> null
        }

        passwordInputLayout.error = when {
            password.isEmpty() -> getString(R.string.error_empty_password)
            password.length < 6 -> getString(R.string.error_short_password)
            else -> null
        }

        return emailInputLayout.error == null && passwordInputLayout.error == null
    }

    private fun performLogin() {
        // Placeholder login
        Snackbar.make(
            findViewById(android.R.id.content),
            "Login successful!",
            Snackbar.LENGTH_SHORT
        ).show()

        navigateToMain()
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
