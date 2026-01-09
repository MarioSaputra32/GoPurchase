package com.pab.gopurchase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pab.gopurchase.databinding.ActivityRegisterBinding
import com.pab.gopurchase.utils.UserPreferences

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init UserPreferences
        userPreferences = UserPreferences(this)

        // Tombol Back
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Tombol ke Login
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Tombol Register
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val name = binding.nameInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val phone = binding.phoneInput.text.toString().trim()
        val address = binding.addressInput.text.toString().trim()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()

        // ===== VALIDASI =====
        when {
            name.isEmpty() -> {
                showToast("Nama tidak boleh kosong")
                return
            }
            email.isEmpty() -> {
                showToast("Email tidak boleh kosong")
                return
            }
            phone.isEmpty() -> {
                showToast("Nomor telepon tidak boleh kosong")
                return
            }
            address.isEmpty() -> {
                showToast("Alamat tidak boleh kosong")
                return
            }
            password.isEmpty() -> {
                showToast("Password tidak boleh kosong")
                return
            }
            password != confirmPassword -> {
                showToast("Password dan konfirmasi tidak cocok")
                return
            }
        }

        // ===== SIMPAN DATA USER =====
        userPreferences.saveUser(
            name = name,
            email = email,
            phone = phone,
            address = address,
            password = password
        )

        showToast("Registrasi berhasil")

        // Pindah ke Login
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
