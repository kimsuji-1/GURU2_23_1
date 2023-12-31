package com.example.guru2_23_1.ui.menu

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.guru2_23_1.R
import com.example.guru2_23_1.ui.DB.DBMember

class MypageActivity : AppCompatActivity() {

    // 프로퍼티들을 Nullable로 변경
    var txtId: TextView? = null
    var txtName: TextView? = null
    var edtExistPassword: EditText? = null
    var edtChangePassword: EditText? = null
    var button_updatepersonal: Button? = null

    lateinit var dbManager: DBMember
    lateinit var sqlitedb: SQLiteDatabase

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        // 뷰들을 findViewById를 사용하여 초기화
        txtId = findViewById(R.id.txtID)
        txtName = findViewById(R.id.textName)
        edtExistPassword = findViewById(R.id.editTextExistPassword)
        edtChangePassword = findViewById(R.id.editTextChangePassword)
        button_updatepersonal = findViewById(R.id.button_updatepersonal)

        dbManager = DBMember(this, "DBMEMBER", null, 1)
        sqlitedb = dbManager.writableDatabase

        val cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM DBMEMBER;", null)
        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndex("ID"))
            val password = cursor.getInt(cursor.getColumnIndex("PASSWORD"))
            val name = cursor.getString(cursor.getColumnIndex("NAME"))
            val region = cursor.getString(cursor.getColumnIndex("REGION"))

            txtId?.setText(id) // Nullable 프로퍼티이므로 ?. 연산자를 사용하여 안전하게 접근
            txtName?.setText(name)

            val spinner_region = findViewById<Spinner>(R.id.spinner_Region)
            val spinner_array =
                arrayOf<String>(
                    "지역 선택",
                    "서울특별시",
                    "인천광역시",
                    "광주광역시",
                    "경기도",
                    "충청북도",
                    "충청남도",
                    "강원영서",
                    "강원영동",
                    "전라북도",
                    "전라남도",
                    "경상북도",
                    "경상남도",
                    "제주도"
                )

            spinner_region.setSelection(spinner_array.indexOf(region))
            spinner_region.adapter = ArrayAdapter.createFromResource(
                this,
                R.array.spinner_array,
                android.R.layout.simple_list_item_1
            )
            spinner_region.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    button_updatepersonal?.setOnClickListener {
                        if (password.toString() == edtExistPassword?.text.toString()) {
                            sqlitedb.execSQL("UPDATE DBMEMBER SET PASSWORD = " + edtChangePassword?.text + " WHERE ID = '" + id + "'")
                            sqlitedb.execSQL("UPDATE DBMEMBER SET REGION = " + spinner_array[position] + " WHERE ID = '" + id + "'")
                            Toast.makeText(applicationContext, "개인정보 수정 완료", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "기존 비밀번호가 틀렸습니다. 다시 입력해주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 데이터베이스 사용이 끝나면 닫기
        sqlitedb.close()
    }
}


