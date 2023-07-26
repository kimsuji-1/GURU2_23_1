package com.example.guru2_23_1.ui

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.example.guru2_23_1.R
import com.example.guru2_23_1.ui.DB.DBDiary

class DiaryActivity : AppCompatActivity() {
    lateinit var dbManager: DBDiary
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var edtDiary: EditText
    lateinit var mood: RatingBar
    lateinit var btnSave: Button
    var score: Float = 0F                       //별점 점수 저장하는 변수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        edtDiary = findViewById(R.id.edtDiary)
        mood = findViewById(R.id.Mood)
        btnSave = findViewById(R.id.btnSave)

        dbManager = DBDiary(this, "DBDIARY", null, 1)
        mood.setOnRatingBarChangeListener{
            mood, rating, fromUser -> score = rating
        }

        btnSave.setOnClickListener {
            var str_diary: String = edtDiary.text.toString()

            if (str_diary == ""){
                Toast.makeText(applicationContext, "하루 일기를 써 주세요", Toast.LENGTH_SHORT).show()
            }else{
                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("INSERT INTO DBDIARY VALUES(NULL, NULL, NULL, NULL, $score, $str_diary)")
            }
        }



    }


}