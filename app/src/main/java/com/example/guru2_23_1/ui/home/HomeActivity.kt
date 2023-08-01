package com.example.guru2_23_1.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.guru2_23_1.R
import com.example.guru2_23_1.ui.home.HomeFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // HomeFragment를 액티비티에 추가합니다.
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()
    }
}