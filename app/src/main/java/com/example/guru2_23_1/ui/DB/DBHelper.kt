package com.example.guru2_23_1.ui.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :SQLiteOpenHelper(context, "memo.db", null, 1, null){
    override fun onCreate(db: SQLiteDatabase?) {
        //id, title, memo, datetime

        val sql =
            "create table note (id integer primary key autoincrement, title text, memo text not null, datetime integer)"
        db!!.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

}