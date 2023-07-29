package com.example.guru2_23_1.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import com.example.guru2_23_1.R

class DayRecordActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_record)

        val buttonMeal = findViewById<Button>(R.id.button_meal)
        buttonMeal.setOnClickListener {
            val intent = Intent(this, MealActivity::class.java)
            startActivity(intent)
        }

        val buttonMood = findViewById<Button>(R.id.button_mood)
        buttonMood.setOnClickListener {
            val intent = Intent(this, DiaryActivity::class.java)
            startActivity(intent)
        }

//        val button: Button = findViewById(R.id.button_meal)
//        btnStart.setOnClickListener {
//            val intent = Intent(this, MealActivity::class.java)
//            startActivity(intent)
//        }

//    fun onLunchClick(view: View) {
//        // 식사 페이지로 전환
//        val intent = Intent(this, LunchActivity::class.java)
//        startActivity(intent)
//    }
//
//    fun onScheduleClick(view: View) {
//        // 일정 페이지로 전환
//        val intent = Intent(this, ScheduleActivity::class.java)
//        startActivity(intent)
//    }
//
//    fun onMoodClick(view: View) {
//        // 기분 페이지로 전환
//        val intent = Intent(this, MoodActivity::class.java)
//        startActivity(intent)
//    }
    }
}

