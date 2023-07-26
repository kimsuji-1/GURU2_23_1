package com.example.guru2_23_1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.guru2_23_1.R
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.EditText
import android.widget.Toast


class MealActivity : AppCompatActivity() {

    lateinit var edtMeal: EditText
    lateinit var edtMealResult: EditText
    lateinit var btnBreakfast: Button
    lateinit var btnLunch: Button
    lateinit var btnDinner: Button
    lateinit var btnSnack: Button
    lateinit var btnSelect: Button

    lateinit var myHelper: myDBHelper
    lateinit var sqlDB: SQLiteDatabase

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


        myHelper = myDBHelper(this)

        btnBreakfast.setOnClickListener {
            sqlDB = myHelper.writableDatabase

            sqlDB.execSQL("INSERT INTO groupTBL VALUES ('"+edtMeal.text.toString()+"');")
            sqlDB.close()
            Toast.makeText(applicationContext, "아침 식사 기록 완료!", Toast.LENGTH_SHORT).show()
        }

        btnLunch.setOnClickListener {
            sqlDB = myHelper.writableDatabase

            sqlDB.execSQL("INSERT INTO groupTBL VALUES ('"+edtMeal.text.toString()+"');")
            sqlDB.close()
            Toast.makeText(applicationContext, "점심 식사 기록 완료!", Toast.LENGTH_SHORT).show()
        }

        btnDinner.setOnClickListener {
            sqlDB = myHelper.writableDatabase

            sqlDB.execSQL("INSERT INTO groupTBL VALUES ('"+edtMeal.text.toString()+"');")
            sqlDB.close()
            Toast.makeText(applicationContext, "저녁 식사 기록 완료!", Toast.LENGTH_SHORT).show()
        }

        btnSnack.setOnClickListener {
            sqlDB = myHelper.writableDatabase

            sqlDB.execSQL("INSERT INTO groupTBL VALUES ('"+edtMeal.text.toString()+"');")
            sqlDB.close()
            Toast.makeText(applicationContext, "간식 기록 완료!", Toast.LENGTH_SHORT).show()
        }


        btnSelect.setOnClickListener {
            sqlDB = myHelper.readableDatabase

            var cursor: Cursor
            cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null)

            var strMeal = "식사 기록" + "\r\n" + "--------" + "\r\n"

            while(cursor.moveToNext()) {
                strMeal += cursor.getString(0) + "\r\n"
            }

            edtMealResult.setText(strMeal)

            cursor.close()
            sqlDB.close()
        }


    }
    inner class myDBHelper(context : Context) : SQLiteOpenHelper(context, "groupDB", null, 1) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE groupTBL (Meal CHAR(20) PRIMARY KEY);")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS groupTBL")
            onCreate(db)
        }
    }
}