package com.example.guru2_23_1.ui.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBCALENDAR(
    context: Context
) : SQLiteOpenHelper(context, "DBCALENDARTABLE", null, 1) {
    companion object {
        const val TABLE_NAME = "DBCALENDARTABLE"
        const val COL_DATE = "DATE"
        const val COL_TODO = "TODO"
        const val COL_SCHEDULE = "SCHEDULE"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE $TABLE_NAME ($COL_DATE CHAR(20), $COL_TODO VARCHAR(50), $COL_SCHEDULE VARCHAR(50))"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun updateEvent(selectedDate: String, type: String, oldContent: String, newContent: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        val selection = "$COL_DATE = ? AND $type = ? AND $COL_TODO = ?"
        val selectionArgs = arrayOf(selectedDate, type, oldContent)

        if (type == "Schedule") {
            values.put(COL_SCHEDULE, newContent)
        } else if (type == "ToDo") {
            values.put(COL_TODO, newContent)
        }

        val affectedRows = db.update(TABLE_NAME, values, selection, selectionArgs)

        db.close()

        return affectedRows > 0
    }

    @SuppressLint("Range")
    fun getToDosForDate(selectedDate: String): List<String> {
        val todoList = mutableListOf<String>()

        // 데이터베이스에서 해당 날짜에 저장된 ToDo 항목을 가져옴
        val db = this.readableDatabase
        val query = "SELECT $COL_TODO FROM $TABLE_NAME WHERE $COL_DATE = ? AND $COL_TODO != ''"
        val cursor = db.rawQuery(query, arrayOf(selectedDate))

        // 데이터를 읽어와서 todoList에 추가
        while (cursor.moveToNext()) {
            val todo = cursor.getString(cursor.getColumnIndex(COL_TODO))
            todoList.add("ToDo: $todo")
        }

        cursor.close()
        db.close()

        return todoList
    }

    fun addEventToDatabase(selectedDate: String, type: String, content: String) {
        val db = writableDatabase

        val values = ContentValues()
        values.put(COL_DATE, selectedDate)
        if (type == "Schedule") {
            values.put(COL_TODO, "")
            values.put(COL_SCHEDULE, content)
        } else if (type == "ToDo") {
            values.put(COL_TODO, content)
            values.put(COL_SCHEDULE, "")
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    @SuppressLint("Range")
    fun getEventsForDate(selectedDate: String): List<String> {
        val eventList = mutableListOf<String>()

        // 데이터베이스에서 해당 날짜에 저장된 일정과 투두를 가져옴
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_DATE = ?"
        val cursor = db.rawQuery(query, arrayOf(selectedDate))

        // 데이터를 읽어와서 eventList에 추가
        while (cursor.moveToNext()) {
            val schedule = cursor.getString(cursor.getColumnIndex(COL_SCHEDULE))
            val todo = cursor.getString(cursor.getColumnIndex(COL_TODO))

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