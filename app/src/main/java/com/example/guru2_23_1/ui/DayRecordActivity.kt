package com.example.guru2_23_1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.guru2_23_1.R

class DayRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_record)

        val buttonMeal = findViewById<Button>(R.id.button_meal)
        val buttonTodo = findViewById<Button>(R.id.button_todo)
        val buttonMood = findViewById<Button>(R.id.button_mood)

        buttonMeal.setOnClickListener {startActivity(Intent(this, MealActivity::class.java))}
        buttonTodo.setOnClickListener {startActivity(Intent(this, SchelduleActivity::class.java))}
        buttonMood.setOnClickListener {startActivity(Intent(this, DiaryActivity::class.java))}

        }
    }

