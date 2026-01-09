package com.pab.gopurchase

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pab.gopurchase.databinding.ActivityLoginBinding
import com.pab.gopurchase.utils.UserPreferences

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)

        // 🔒 Jika sudah login, langsung ke MainActivity
        if (userPreferences.isLogin()) {
            navigateToMain()
            return
        }

        // Tombol Login
        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        // Tombol Register
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun performLogin() {
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passwordInput.text.toString()

        // ===== VALIDASI =====
        when {
            email.isEmpty() || password.isEmpty() -> {
                showToast("Email dan password harus diisi")
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showToast("Format email tidak valid")
                return
            }
        }

        // ===== LOGIN VIA UserPreferences =====
        val success = userPreferences.login(email, password)

        if (success) {
            showToast("Login berhasil")
            navigateToMain()
        } else {
            showToast("Email atau password salah")
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
