package com.pab.gopurchase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import com.pab.gopurchase.adapters.OnboardingAdapter
import com.pab.gopurchase.models.OnboardingItem

class OnboardingActivity : AppCompatActivity() {
    
    private lateinit var viewPager: ViewPager2
    private lateinit var dotsIndicator: LinearLayout
    private lateinit var btnNext: MaterialButton
    private lateinit var btnSkip: MaterialButton
    
    private val onboardingItems = listOf(
        OnboardingItem(
            R.drawable.onboarding_1,
            R.string.onboarding_desc_1
        ),
        OnboardingItem(
            R.drawable.onboarding_2,
            R.string.onboarding_desc_2
        ),
        OnboardingItem(
            R.drawable.onboarding_3,
            R.string.onboarding_desc_3
        )
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        
        initViews()
        setupViewPager()
        setupDots()
        setupButtons()
    }
    
    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        dotsIndicator = findViewById(R.id.dotsIndicator)
        btnNext = findViewById(R.id.btnNext)
        btnSkip = findViewById(R.id.btnSkip)
    }
    
    private fun setupViewPager() {
        val adapter = OnboardingAdapter(onboardingItems)
        viewPager.adapter = adapter
        
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentDot(position)
                
                // Change button text on last page
                if (position == onboardingItems.size - 1) {
                    btnNext.text = getString(R.string.get_started)
                    btnSkip.visibility = View.GONE
                } else {
                    btnNext.text = getString(R.string.next)
                    btnSkip.visibility = View.VISIBLE
                }
            }
        })
    }
    
    private fun setupDots() {
        val dots = arrayOfNulls<ImageView>(onboardingItems.size)
        
        for (i in dots.indices) {
            dots[i] = ImageView(this)
            dots[i]?.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.dot_inactive)
            )
            
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            
            dotsIndicator.addView(dots[i], params)
        }
        
        setCurrentDot(0)
    }
    
    private fun setCurrentDot(position: Int) {
        val childCount = dotsIndicator.childCount
        for (i in 0 until childCount) {
            val imageView = dotsIndicator.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.dot_active)
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.dot_inactive)
                )
            }
        }
    }
    
    private fun setupButtons() {
        btnNext.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < onboardingItems.size - 1) {
                viewPager.currentItem = currentItem + 1
            } else {
                navigateToLogin()
            }
        }
        
        btnSkip.setOnClickListener {
            navigateToLogin()
        }
    }
    
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
