package com.pab.gopurchase

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pab.gopurchase.fragments.*

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        bottomNavigation = findViewById(R.id.bottomNavigation)

        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.nav_home
            loadFragment(HomeFragment())
        }

        setupBottomNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }

            R.id.action_notifications -> {
                startActivity(Intent(this, NotificationsActivity::class.java))
                true
            }

            R.id.action_cart -> {
                val cartFragment = CartFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, cartFragment)
                    .commit()

                bottomNavigation.menu.findItem(R.id.nav_cart)?.isChecked = true

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            val fragment: Fragment? = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_categories -> ProductFragment()
                R.id.nav_cart -> CartFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> null
            }

            fragment?.let {
                loadFragment(it)
                true
            } ?: false
        }
    }


    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
