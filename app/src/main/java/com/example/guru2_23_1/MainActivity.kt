package com.example.guru2_23_1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.guru2_23_1.ui.calender.CalenderFragment
import com.example.guru2_23_1.ui.home.HomeFragment
import com.example.guru2_23_1.ui.menu.LogoutActivity
import com.example.guru2_23_1.ui.menu.MypageActivity
import com.example.guru2_23_1.ui.notifications.NotificationsFragment

class MainActivity : AppCompatActivity() {
    private val fl: FrameLayout by lazy{
        findViewById(R.id.nav_host_fragment_activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.show()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_personal_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_mypage -> {
                val intent = Intent(this, MypageActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_logout -> {
                val intent = Intent(this, LogoutActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}

//    private lateinit var binding: ActivityMainBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//    }
//}