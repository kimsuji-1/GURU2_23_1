package com.example.guru2_23_1.ui.DB

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.guru2_23_1.ui.calender.CalenderFragment

class DBMember(
    context: CalenderFragment,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int) :
    SQLiteOpenHelper(context, "DBMEMBERTABLE", null, 1) {
    override fun onCreate(MyDB: SQLiteDatabase) {
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS DBMEMBER (ID VARCHAR(20) PRIMARY KEY, PASSWORD FLOAT(5), NAME VARCHAR(20), REGION CHAR(20))")
    }

    override fun onUpgrade(MyDB: SQLiteDatabase, i: Int, i1: Int) {
        MyDB.execSQL("DROP TABLE IF EXISTS DBMEMBER")
    }

    fun checkUserpass(username: String, password: String) : Boolean {
        val MyDB = this.writableDatabase
        var res = true
        val cursor = MyDB.rawQuery(
            "Select * from DBMEMBER where usermame = ? and password = ?",
            arrayOf(username, password)
        )
        if (cursor.count <= 0) res= false
        return res
    }

    companion object {
        const val DBNAME = "DBMember"
    }
}