package com.example.guru2_23_1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.guru2_23_1.R

class DayRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_record)

        fun onMealClick(v: View) {
            val intent = Intent(this, MealActivity::class.java)
            startActivity(intent)
        }

//        fun onScheduleClick(v: View) {
//            val intent = Intent(this, MealActivity::class.java)
//            startActivity(intent)
//        }

        fun onMoodClick(v: View) {
            val intent = Intent(this, DiaryActivity::class.java)
            startActivity(intent)
        }


    }


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

