package com.example.guru2_23_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.example.guru2_23_1.MealActivity

class MealActivity : AppCompatActivity() {

    private lateinit var meal_btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("식사")

        meal_btn = findViewById(R.id.meal_btn)
    }
}