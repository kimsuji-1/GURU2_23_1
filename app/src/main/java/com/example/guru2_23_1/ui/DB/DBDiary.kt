package com.example.guru2_23_1.ui.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBDiary (context: Context,
               name: String?,
               factory: SQLiteDatabase.CursorFactory?,
               version: Int
    ): SQLiteOpenHelper(context, "DBDIARYTABLE", null, 1){
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(
                "CREATE TABLE DBDIARY (DATE DATE, MOOD FLOAT(5), DIARY VARCHAR(100))")
        }

        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        }

    }
