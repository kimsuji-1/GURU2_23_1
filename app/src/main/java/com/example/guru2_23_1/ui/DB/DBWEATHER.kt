package com.example.guru2_23_1.ui.DB

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.guru2_23_1.ui.calender.CalenderFragment

class DBWEATHER(
    context: CalenderFragment,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
    ): SQLiteOpenHelper(context, "DBWEATHER", null, 1){
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(
                "CREATE TABLE DBWEATHER (WH_REGION CHAR(20), DATE date, WEATHER CHAR(20))")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS DBWEATHER")
            onCreate(db)
        }

    }