package com.example.guru2_23_1.ui.DB

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.guru2_23_1.ui.calender.CalenderFragment

class DBDiary(
    context: CalenderFragment,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
    ): SQLiteOpenHelper(context, "DBDIARYTABLE", null, 1){
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(
                "CREATE TABLE DBDIARY (DATE CHAR(20), MOOD FLOAT(5), DIARY VARCHAR(100))")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS DBDIARY")
            onCreate(db)
        }

    }
