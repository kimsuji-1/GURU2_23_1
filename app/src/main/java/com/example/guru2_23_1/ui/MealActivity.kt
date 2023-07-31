package com.example.guru2_23_1.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.guru2_23_1.R
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.guru2_23_1.ui.DB.DBMeal
import java.text.SimpleDateFormat


class MealActivity : AppCompatActivity() {

    lateinit var edtMeal: EditText
    lateinit var btnBreakfast: Button
    lateinit var btnLunch: Button
    lateinit var btnDinner: Button
    lateinit var btnSnack: Button
    lateinit var btnSelect: Button

    lateinit var dbManager: DBMeal
    lateinit var sqlDB: SQLiteDatabase

    lateinit var layout: LinearLayout
    //    lateinit var str_date: String
    lateinit var dateLayout: LinearLayout
    lateinit var txtCurrentDate: TextView
    lateinit var str_meal: String

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {

        val currentTime : Long = System.currentTimeMillis() // ms로 반환
        val dateformat = SimpleDateFormat("yyyy-MM-dd") // 년 월 일
        val date = dateformat.format(currentTime)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        // 액션바에 '오늘의 식사' 문구 표시
        supportActionBar?.title = "오늘의 식사"

        edtMeal = findViewById(R.id.edtMeal)

        btnBreakfast = findViewById(R.id.btnBreakfast)
        btnLunch = findViewById(R.id.btnLunch)
        btnDinner = findViewById(R.id.btnDinner)
        btnSnack = findViewById(R.id.btnSnack)
        btnSelect = findViewById(R.id.btnSelect)

        layout = findViewById(R.id.meal_list)

        dbManager = DBMeal(this, "DBMeal", null, 1)

        btnBreakfast.setOnClickListener {
            Toast_NoMeal()
            sqlDB = dbManager.writableDatabase

            sqlDB.execSQL("INSERT INTO DBMEAL VALUES ("+ date + ", '"+edtMeal.text.toString()+"');")
            sqlDB.close()
            Toast.makeText(applicationContext, "아침 식사 기록 완료!", Toast.LENGTH_SHORT).show()
        }

        btnLunch.setOnClickListener {
            Toast_NoMeal()
            sqlDB = dbManager.writableDatabase

            sqlDB.execSQL("INSERT INTO DBMEAL VALUES ('"+ date + "', '"+edtMeal.text.toString()+"');")
            sqlDB.close()
            Toast.makeText(applicationContext, "점심 식사 기록 완료!", Toast.LENGTH_SHORT).show()
        }

        btnDinner.setOnClickListener {
            Toast_NoMeal()
            sqlDB = dbManager.writableDatabase

            sqlDB.execSQL("INSERT INTO DBMEAL VALUES ('"+ date + "', '"+edtMeal.text.toString()+"');")
            sqlDB.close()
            Toast.makeText(applicationContext, "저녁 식사 기록 완료!", Toast.LENGTH_SHORT).show()
        }

        btnSnack.setOnClickListener {
            Toast_NoMeal()
            sqlDB = dbManager.writableDatabase

            sqlDB.execSQL("INSERT INTO DBMEAL VALUES ('"+ date + "', '"+edtMeal.text.toString()+"');")
            sqlDB.close()
            Toast.makeText(applicationContext, "간식 기록 완료!", Toast.LENGTH_SHORT).show()
        }


        btnSelect.setOnClickListener {
            sqlDB = dbManager.readableDatabase

            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM DBMEAL WHERE DATE = '"+date+"';", null)

            txtCurrentDate = findViewById(R.id.txtCurrentDate)
            dateLayout = findViewById(R.id.dateLayout)

            txtCurrentDate.setText(date)
            dateLayout.visibility = View.VISIBLE

            var num: Int = 0
            while(cursor.moveToNext()) {
                str_meal = cursor.getString(cursor.getColumnIndex("MEAL")).toString()

                var layout_item = LinearLayout(this)
                layout_item.orientation = LinearLayout.VERTICAL
                layout_item.id = num

                var tvMeal = TextView(this)
                tvMeal.text = str_meal
                tvMeal.textSize = 20f
                layout_item.addView(tvMeal)

                layout.addView(layout_item)
                num++
            }


            cursor.close()
            sqlDB.close()
        }
    }

    private fun Toast_NoMeal(){
        if(edtMeal.text.toString()=="") {
            Toast.makeText(applicationContext, "식사를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }
}