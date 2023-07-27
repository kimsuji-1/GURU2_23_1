package com.example.guru2_23_1.ui.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBMeal (context: Context,
               name: String?,
               factory: SQLiteDatabase.CursorFactory?,
               version: Int
): SQLiteOpenHelper(context, "DBMEALTABLE", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(
            "CREATE TABLE DBMEAL (DATE DATE, MEAL VARCHAR(50))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS DBMEAL")
        onCreate(db)
    }

}