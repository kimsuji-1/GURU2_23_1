package com.example.guru2_23_1.ui.menu

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.guru2_23_1.R
import com.example.guru2_23_1.ui.MealActivity
import com.example.guru2_23_1.ui.home.HomeFragment

class LogoutActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)


        val buttonLogout = findViewById<Button>(R.id.button_logout)
        buttonLogout.setOnClickListener {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
        }
    }
}