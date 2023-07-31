package com.example.guru2_23_1.ui.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBCALENDAR(
    context: Context
) : SQLiteOpenHelper(context, "DBCALENDARTABLE", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE DBCALENDARTABLE (DATE CHAR(20), TODO VARCHAR(50), SCHEDULE VARCHAR(50))"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS DBCALENDARTABLE")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun getToDosForDate(selectedDate: String): List<String> {
        val todoList = mutableListOf<String>()

        // 데이터베이스에서 해당 날짜에 저장된 ToDo 항목을 가져옴
        val db = this.readableDatabase
        val query = "SELECT TODO FROM DBCALENDARTABLE WHERE DATE = ? AND TODO != ''"
        val cursor = db.rawQuery(query, arrayOf(selectedDate))

        // 데이터를 읽어와서 todoList에 추가
        while (cursor.moveToNext()) {
            val todo = cursor.getString(cursor.getColumnIndex("TODO"))
            todoList.add("ToDo: $todo")
        }

        cursor.close()
        db.close()

        return todoList
    }

    fun addEventToDatabase(selectedDate: String, type: String, content: String) {
        val db = writableDatabase

        val values = ContentValues()
        values.put("DATE", selectedDate)
        if (type == "Schedule") {
            values.put("TODO", "")
            values.put("SCHEDULE", content)
        } else if (type == "ToDo") {
            values.put("TODO", content)
            values.put("SCHEDULE", "")
        }

        db.insert("DBCALENDARTABLE", null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getEventsForDate(selectedDate: String): List<String> {
        val eventList = mutableListOf<String>()

        // 데이터베이스에서 해당 날짜에 저장된 일정과 투두를 가져옴
        val db = this.readableDatabase
        val query = "SELECT * FROM DBCALENDARTABLE WHERE DATE = ?"
        val cursor = db.rawQuery(query, arrayOf(selectedDate))

        // 데이터를 읽어와서 eventList에 추가
        while (cursor.moveToNext()) {
            val schedule = cursor.getString(cursor.getColumnIndex("SCHEDULE"))
            val todo = cursor.getString(cursor.getColumnIndex("TODO"))

            if (schedule.isNotEmpty()) {
                eventList.add("Schedule: $schedule")
            }

            if (todo.isNotEmpty()) {
                eventList.add("ToDo: $todo")
            }
        }

        cursor.close()
        db.close()

        return eventList
    }
}
