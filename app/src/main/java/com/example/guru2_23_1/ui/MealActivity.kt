package com.example.guru2_23_1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.guru2_23_1.R
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.EditText
import android.widget.Toast
import com.example.guru2_23_1.ui.DB.DBMeal
import java.text.SimpleDateFormat


class MealActivity : AppCompatActivity() {

    lateinit var edtMeal: EditText
    lateinit var edtMealResult: EditText
    lateinit var btnBreakfast: Button
    lateinit var btnLunch: Button
    lateinit var btnDinner: Button
    lateinit var btnSnack: Button
    lateinit var btnSelect: Button

    lateinit var dbManager: DBMeal
    lateinit var sqlDB: SQLiteDatabase

    val currentTime : Long = System.currentTimeMillis() // ms로 반환
    val dateformat = SimpleDateFormat("yyyy-MM-dd") // 년 월 일
    val date = dateformat.format(currentTime)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        edtMeal = findViewById(R.id.edtMeal)
        edtMealResult = findViewById(R.id.edtMealResult)

        btnBreakfast = findViewById(R.id.btnBreakfast)
        btnLunch = findViewById(R.id.btnLunch)
        btnDinner = findViewById(R.id.btnDinner)
        btnSnack = findViewById(R.id.btnSnack)
        btnSelect = findViewById(R.id.btnSelect)

        dbManager = DBMeal(this, "DBMeal", null, 1)

        btnBreakfast.setOnClickListener {
            sqlDB = dbManager.writableDatabase

            sqlDB.execSQL("INSERT INTO DBMEAL VALUES ("+ date + ", '"+edtMeal.text.toString()+"');")
            sqlDB.close()
            Toast.makeText(applicationContext, "아침 식사 기록 완료!", Toast.LENGTH_SHORT).show()
        }

        btnLunch.setOnClickListener {
            sqlDB = dbManager.writableDatabase

            sqlDB.execSQL("INSERT INTO DBMEAL VALUES ("+ date + ", '"+edtMeal.text.toString()+"');")
            sqlDB.close()
            Toast.makeText(applicationContext, "점심 식사 기록 완료!", Toast.LENGTH_SHORT).show()
        }

        btnDinner.setOnClickListener {
            sqlDB = dbManager.writableDatabase

            sqlDB.execSQL("INSERT INTO DBMEAL VALUES ("+ date + ", '"+edtMeal.text.toString()+"');")
            sqlDB.close()
            Toast.makeText(applicationContext, "저녁 식사 기록 완료!", Toast.LENGTH_SHORT).show()
        }

        btnSnack.setOnClickListener {
            sqlDB = dbManager.writableDatabase

            sqlDB.execSQL("INSERT INTO DBMEAL VALUES ("+ date + ", '"+edtMeal.text.toString()+"');")
            sqlDB.close()
            Toast.makeText(applicationContext, "간식 기록 완료!", Toast.LENGTH_SHORT).show()
        }


        btnSelect.setOnClickListener {
            sqlDB = dbManager.readableDatabase

            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM DBMEAL;", null)

            var strMeal = "식사 기록" + "\r\n" + "--------" + "\r\n"

            while(cursor.moveToNext()) {
                strMeal += cursor.getString(0) + "\r\n"
            }

            edtMealResult.setText(strMeal)

            cursor.close()
            sqlDB.close()
        }
    }
}