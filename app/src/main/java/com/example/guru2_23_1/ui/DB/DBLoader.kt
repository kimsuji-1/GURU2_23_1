package com.example.guru2_23_1.ui.DB

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import java.util.Date

class DBLoader(context: Context) {

    private val context = context
    private var db: DBHelper

    init{
        db = DBHelper(context);
    }

    fun save(title: String, memo:String){
        val date = Date()
        val contentValues = ContentValues()
        contentValues.put("title", title)
        contentValues.put("memo", memo)
        contentValues.put("datetime", date.time)

        db.writableDatabase.insert("note", null, contentValues)
        db.close()
        Toast.makeText(context, "저장됨", Toast.LENGTH_SHORT).show()

    }

    fun delete(id: Int){
        db.writableDatabase.delete("note", "id=?", arrayOf(id.toString()))
        db.close()
        Toast.makeText(context, "삭제됨", Toast.LENGTH_SHORT).show()
    }

    fun meemoList(datetime: Int){

    }
}