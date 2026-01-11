package com.pab.gopurchase.utils

import android.content.Context
import com.pab.gopurchase.models.User


class UserPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PHONE = "phone"
        private const val KEY_ADDRESS = "address"
        private const val KEY_PASSWORD = "password"
        private const val KEY_IS_LOGIN = "is_login"
    }

    fun saveUser(name: String, email: String, phone: String, address: String, password: String) {
        prefs.edit().apply {
            putString(KEY_NAME, name)
            putString(KEY_EMAIL, email)
            putString(KEY_PHONE, phone)
            putString(KEY_ADDRESS, address)
            putString(KEY_PASSWORD, password)
            apply()
        }
    }

    fun login(email: String, password: String): Boolean {
        val savedEmail = prefs.getString(KEY_EMAIL, null)
        val savedPassword = prefs.getString(KEY_PASSWORD, null)

        val success = email == savedEmail && password == savedPassword
        if (success) setLogin(true)
        return success
    }

    fun setLogin(status: Boolean) {
        prefs.edit().putBoolean(KEY_IS_LOGIN, status).apply()
    }

    fun isLogin(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGIN, false)
    }

    fun logout() {
        prefs.edit()
            .putBoolean(KEY_IS_LOGIN, false)
            .apply()
    }

    fun getUser(): User? {
        val name = prefs.getString(KEY_NAME, null) ?: return null
        val email = prefs.getString(KEY_EMAIL, null) ?: return null
        val phone = prefs.getString(KEY_PHONE, null) ?: return null
        val address = prefs.getString(KEY_ADDRESS, null) ?: return null

        return User(name, email, phone, address)
    }
}
