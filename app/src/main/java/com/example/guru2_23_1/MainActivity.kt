package com.example.guru2_23_1

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.guru2_23_1.ui.calender.CalenderFragment
import com.example.guru2_23_1.ui.home.HomeFragment
import com.example.guru2_23_1.ui.notifications.NotificationsFragment

class MainActivity : AppCompatActivity() {
    private val fl: FrameLayout by lazy{
        findViewById(R.id.nav_host_fragment_activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nav_view = findViewById<BottomNavigationView>(R.id.nav_view)

        //supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment_activity_main, HomeFragment()).commit()

        nav_view.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.navigation_home -> {
                        nav_view.itemIconTintList =
                            ContextCompat.getColorStateList(this, R.color.black)
                        nav_view.itemTextColor =
                            ContextCompat.getColorStateList(this, R.color.black)
                        HomeFragment()
                        //Respond to navigation item home click
                    }

                    R.id.navigation_calendar -> {
                        nav_view.itemIconTintList =
                            ContextCompat.getColorStateList(this, R.color.black)
                        nav_view.itemTextColor =
                            ContextCompat.getColorStateList(this, R.color.black)
                        CalenderFragment()
                        //Respond to navigation item calendar click
                    }

                    else -> {
                        nav_view.itemIconTintList =
                            ContextCompat.getColorStateList(this, R.color.black)
                        nav_view.itemTextColor =
                            ContextCompat.getColorStateList(this, R.color.black)
                        NotificationsFragment()
                        //Respond to navigation item notifications click
                    }
                }
            )
            true
        }
        nav_view.selectedItemId = R.id.navigation_home
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main,fragment)
            .commit()
    }
}