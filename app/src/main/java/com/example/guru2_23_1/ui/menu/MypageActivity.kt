package com.example.guru2_23_1.ui.menu

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.guru2_23_1.R
import com.example.guru2_23_1.ui.DB.DBMember

class MypageActivity : AppCompatActivity() {

    lateinit var txtId: TextView
    lateinit var txtName: TextView
    lateinit var edtExistPassword: EditText
    lateinit var edtChangePassword: EditText

    lateinit var dbManager: DBMember
    lateinit var sqlitedb: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        dbManager = DBMember(this, "DBMEMBER", null, 1)
        sqlitedb = dbManager.readableDatabase
        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM DBMEMBER WHERE;", null)
        while(cursor.moveToNext()){
            var id = cursor.getString(cursor.getColumnIndex("ID"))
            var password = cursor.getInt(cursor.getColumnIndex("PASSWORD"))
            var name = cursor.getString(cursor.getColumnIndex("NAME"))
            var region = cursor.getString(cursor.getColumnIndex("REGION"))

            txtId.setText(id)
            txtName.setText(name)
        }
        sqlitedb.close()


        val spinner_region = findViewById<Spinner>(R.id.spinner_Region)

        spinner_region.adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_list_item_1)
        spinner_region.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                지역이 저장되어있는 postion변수 DBMEMEBER에 지역 수정
                sqlitedb = dbManager.writeableDatabase

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
    }