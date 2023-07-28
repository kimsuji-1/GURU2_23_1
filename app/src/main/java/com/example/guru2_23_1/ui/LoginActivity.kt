package com.example.guru2_23_1.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2_23_1.databinding.ActivityLoginBinding
import com.example.guru2_23_1.ui.DB.DBMember
import com.example.guru2_23_1.ui.notifications.NotificationsFragment

class LoginActivity : AppCompatActivity() {
    lateinit var loginBinding: ActivityLoginBinding
    var DB:DBMember?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(loginBinding.root)
        DB = DBMember(this, "DBMEMBER", null, 1)

        loginBinding.loginBtn!!.setOnClickListener {
            val user = loginBinding.nickname!!.text.toString()
            val pass = loginBinding.password!!.text.toString()
            if (user == ""|| pass=="") Toast.makeText(
                this@LoginActivity,
                "회원 정보를 모두 입력해주세요",
                Toast.LENGTH_SHORT
            ).show() else{
                val checkUserpass = DB!!.checkUserpass(user, pass)
                if (checkUserpass==true){
                    Toast.makeText(this@LoginActivity, "로그인 완료!", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(applicationContext, NotificationsFragment::class.java) //로그인 성공 창이동
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this@LoginActivity, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        loginBinding.registerBtn.setOnClickListener {
            val loginIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(loginIntent)
        }
    }
}