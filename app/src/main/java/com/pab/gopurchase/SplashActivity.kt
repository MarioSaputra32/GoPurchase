package com.pab.gopurchase

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.pab.gopurchase.R

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_DELAY = 2000L // 2 seconds
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Navigate to Onboarding after delay
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToOnboarding()
        }, SPLASH_DELAY)
    }

    private fun navigateToOnboarding() {
        val intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
        finish()
    }
}
