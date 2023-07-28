package com.example.guru2_23_1.ui.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBMember(context: Context,
               name: String?,
               factory: SQLiteDatabase.CursorFactory?,
               version: Int) :
    SQLiteOpenHelper(context, "DBMember", null, 1) {
    override fun onCreate(MyDB: SQLiteDatabase) {
        MyDB.execSQL("CREATE TABLE DBMEMBERTABLE(ID VARCHAR(20) PRIMARY KEY, PASSWORD FLOAT(5), NAME VARCHAR(20), REGION CHAR(20))")
    }

    override fun onUpgrade(MyDB: SQLiteDatabase, i: Int, i1: Int) {
        MyDB.execSQL("DROP TABLE IF EXISTS DBMEMBER")
    }

    fun insertData(username: String?, password: String?) : Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val result = MyDB.insert("users", null, contentValues)
        return if (result == -1L) false else true
    }

    fun checkUsername(username: String) : Boolean {
        val MyDB = this.writableDatabase
        var res = true
        val cursor = MyDB.rawQuery("Select * from users where username = ?", arrayOf(username))
        if (cursor.count <= 0) res = false
        return res
    }

    fun checkUserpass(username: String, password: String) : Boolean {
        val MyDB = this.writableDatabase
        var res = true
        val cursor = MyDB.rawQuery(
            "Select * from users where usermame = ? and password = ?",
            arrayOf(username, password)
        )
        if (cursor.count <= 0) res= false
        return res
    }

    companion object {
        const val DBNAME = "DBMember"
    }
}