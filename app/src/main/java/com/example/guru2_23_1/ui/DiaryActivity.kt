package com.example.guru2_23_1.ui

import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.guru2_23_1.R
import com.example.guru2_23_1.ui.DB.DBDiary
import java.text.SimpleDateFormat

@RequiresApi(Build.VERSION_CODES.O)
class DiaryActivity : AppCompatActivity() {
    lateinit var dbManager: DBDiary
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var edtDiary: EditText
    lateinit var mood: RatingBar
    lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        // 액션바에 'Today's Diary' 문구 표시
        supportActionBar?.title = "Today's Diary"

        val currentTime : Long = System.currentTimeMillis() // ms로 반환
        val dateformat = SimpleDateFormat("yyyy-MM-dd") // 년 월 일
        val date = dateformat.format(currentTime)

        edtDiary = findViewById(R.id.edtDiary)
        mood = findViewById(R.id.Mood)
        btnSave = findViewById(R.id.btnSave)

        dbManager = DBDiary(this, "DBDIARY", null, 1)

        mood.setOnRatingBarChangeListener { mood, rating, fromUser ->
//            Toast.makeText(applicationContext,"$rating 점", Toast.LENGTH_SHORT).show()
        }

        btnSave.setOnClickListener {
            var str_diary: String = edtDiary.text.toString()

            if (str_diary == ""){
                Toast.makeText(applicationContext, "하루 일기를 써 주세요", Toast.LENGTH_SHORT).show()
            }else{
                sqlitedb = dbManager.writableDatabase
                Log.d("save_date", date)
                sqlitedb.execSQL("INSERT INTO DBDIARY VALUES ('" + date + "', " + mood.rating + ", '" + str_diary + "');"
                )
                sqlitedb.close()
                Toast.makeText(applicationContext, "저장되었습니다", Toast.LENGTH_SHORT).show()
            }
        }

    }

}