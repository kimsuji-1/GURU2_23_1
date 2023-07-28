package com.example.guru2_23_1.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2_23_1.R
import com.example.guru2_23_1.databinding.ActivityRegisterBinding
import com.example.guru2_23_1.ui.DB.DBDiary
import com.example.guru2_23_1.ui.DB.DBMember
import org.w3c.dom.Text

class RegisterActivity : AppCompatActivity() {
//    lateinit var registerBinding: ActivityRegisterBinding
    var inputRegisterBtn: Button = findViewById(R.id.inputRegisterBtn)
    var edt_Id: EditText = findViewById(R.id.ID)
    var edt_Password: EditText = findViewById(R.id.PASSWORD)
    var edt_reInputPassword: EditText = findViewById(R.id.reInputPassword)
    var edt_Name: EditText = findViewById(R.id.NAME)
    var region_spinner: Spinner = findViewById(R.id.region_spinner)
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var dbManager: DBMember
        lateinit var sqlitedb: SQLiteDatabase

        dbManager = DBMember(this, "DBMEMBER", null, 1)
        sqlitedb = dbManager.writableDatabase

        val spinner_array =
            arrayOf<String>("지역 선택", "서울특별시", "인천광역시", "광주광역시", "경기도", "충청북도", "충청남도", "강원영서", "강원영동", "전라북도", "전라남도", "경상북도", "경상남도", "제주도")

        region_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                inputRegisterBtn.setOnClickListener {
                    val strID = edt_Id.text.toString()
                    val floatpass = edt_Password.text.toString()
                    val floatrepass = edt_reInputPassword.text.toString()
                    val strName = edt_Name.text.toString()
                    lateinit var strRegion: Text

//                회원정보를 다 안 적은 경우
                    if (strID == ""|| floatpass == "" || floatrepass == ""||strName == "") Toast.makeText(
                        applicationContext,
                        "회원 정보를 모두 입력하세요.",
                        Toast.LENGTH_SHORT).show()
//                회원정보를 빠짐없이 다 적은 경우
                    else {
                        if (floatpass == floatrepass) {                     //비밀번호와 비밀번호확인이 일치
                            //이미 아이디가 있다면?
                            var cursor: Cursor
                            cursor = sqlitedb.rawQuery("SELECT ID FROM DBMEMBER;", null)
                            while(cursor.moveToNext()) {
                                var id = cursor.getString(cursor.getColumnIndex("ID"))
                                if (id.toString() == strID) {
                                    Toast.makeText(applicationContext, "해당 아이디를 사용하실 수 없습니다.(아이디중복)", Toast.LENGTH_SHORT).show()
                                }
                            }
                            sqlitedb.execSQL(
                                "INSERT INTO DBMEMBER VALUES('" + strID + "', " + floatpass + ", '" + strName + "', '" + spinner_array[position] + "')"
                            )                       //DBMEMBER에 저장
                            Toast.makeText(applicationContext, "가입 완료!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }
}