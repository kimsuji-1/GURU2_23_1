package com.example.guru2_23_1.ui.DB

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.guru2_23_1.ui.model.Memo
import java.util.Calendar
import java.util.Date

class DBLoader(context: Context) {

    private val context = context
    private var db: DBHelper

    init{
        db = DBHelper(context);
    }

    fun save(title: String, memo:String){
        val calendar = Calendar.getInstance()
        Log.d("hhh", calendar.get(Calendar.YEAR).toString())
        Log.d("hhh", calendar.timeInMillis.toString())
        val contentValues = ContentValues()
        contentValues.put("title", title)
        contentValues.put("memo", memo)
        contentValues.put("datetime", calendar.timeInMillis)

//        db.writableDatabase.insert("note", null, contentValues)
//        db.close()
        Toast.makeText(context, "저장됨", Toast.LENGTH_SHORT).show()

    }

    fun delete(id: Int){
        db.writableDatabase.delete("note", "id=?", arrayOf(id.toString()))
        db.close()
        Toast.makeText(context, "삭제됨", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("Range")
    fun meemoList(datetime: Int?): ArrayList<Memo>{
        val array = ArrayList<Memo>()
        var sql = ""
        if(datetime == null){
            sql = "select * from note order by datetime desc"
        }else{
            sql = "select * from note where datetime = "+ datetime +" note order by datetime desc"
        }
        val cursor = db.readableDatabase.rawQuery(sql, null)
        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val memo = cursor.getString(cursor.getColumnIndex("memo"))
            val getDatetime = cursor.getInt(cursor.getColumnIndex("datetime"))

            val memoItem = Memo(id, title, memo, getDatetime)
            array.add(memoItem)
        }

        return array
    }
}