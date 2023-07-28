package com.example.guru2_23_1.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2_23_1.databinding.ActivityRegisterBinding
import com.example.guru2_23_1.ui.DB.DBMember

class RegisterActivity : AppCompatActivity() {
    lateinit var registerBinding: ActivityRegisterBinding
    var DB:DBMember?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(registerBinding.root)
        DB = DBMember(this)

        registerBinding.inputRegisterBtn.setOnClickListener {
            val user = registerBinding.ID.text.toString()
            val pass = registerBinding.PASSWORD.text.toString()
            val repass = registerBinding.reInputPassword.text.toString()
            if (user == ""|| pass == "" || repass == "") Toast.makeText(
                this@RegisterActivity,
                "회원 정보를 모두 입력하세요.",
                Toast.LENGTH_SHORT
            ).show() else {
                if (pass == repass) {
                    val checkUsername = DB!!.checkUsername(user)
                    if (checkUsername == false) {
                        val insert = DB!!.insertData(user, pass)
                        if (insert == true) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "가입 완료!",
                                Toast.LENGTH_SHORT
                            ).show()

                            val name = registerBinding.NAME.text.toString()
                            val region = registerBinding.regionSpinner.selectedItem.toString()

                            DB.insertData(name, region)

                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                        } else{
                            Toast.makeText(
                                this@RegisterActivity,
                                "비밀번호가 일치하지 않습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "이미 가입된 회원입니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}