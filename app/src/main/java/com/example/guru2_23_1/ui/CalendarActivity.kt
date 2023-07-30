package com.example.guru2_23_1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.guru2_23_1.R

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)


        val buttontoday = findViewById<Button>(R.id.today_btn)
        buttontoday.setOnClickListener {
            val intent = Intent(this, MonthActivity::class.java)
            startActivity(intent)
        }

//        val buttonTodo = findViewById<Button>(R.id.month_year_tv)
//        buttonTodo.setOnClickListener {
//            val intent = Intent(this, DayRecordActivity::class.java)
//            startActivity(intent)
//        }

        val buttonadd = findViewById<Button>(R.id.add_btn)
        buttonadd.setOnClickListener {
            val intent = Intent(this, addActivity::class.java)
            startActivity(intent)
        }
    }
}