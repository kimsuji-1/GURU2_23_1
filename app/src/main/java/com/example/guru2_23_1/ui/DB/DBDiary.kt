package com.example.guru2_23_1.ui.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.guru2_23_1.ui.calender.CalenderFragment

class DBDiary(
    context: Context,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, "DBDIARYTABLE", factory, version){
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(
                "CREATE TABLE DBDIARY (DATE CHAR(20), MOOD FLOAT(5), DIARY VARCHAR(100))")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS DBDIARY")
            onCreate(db)
        }

    }
