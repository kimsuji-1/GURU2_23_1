package com.example.guru2_23_1.ui.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBDiary (context: Context,
               name: String?,
               factory: SQLiteDatabase.CursorFactory?,
               version: Int
    ): SQLiteOpenHelper(context, name, factory, version){
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(
                "CREATE TABLE DBDIARY (ID VARCHAR(20) PRIMARY KEY, MEAL VARCHAR(50), TODO VARCHAR(50), SCHEDULE VARCHAR(50), MOOD FLOAT(5), DIARY VARCHAR(100))")
        }

        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        }

    }
