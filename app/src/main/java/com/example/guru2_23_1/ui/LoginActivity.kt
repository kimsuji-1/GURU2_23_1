package com.example.guru2_23_1.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2_23_1.MainActivity
import com.example.guru2_23_1.R
import com.example.guru2_23_1.databinding.ActivityLoginBinding
import com.example.guru2_23_1.ui.DB.DBMember

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private var DB: DBMember? = null

    companion object {
        const val REQUEST_MAIN_ACTIVITY = 1
    }

    private fun moveToMainActivity() {
        val loginCheck = 1 // 로그인 성공 시 1, 실패 시 0으로 설정하거나, 실제로 로그인 성공 여부를 확인한 후 값을 설정해야 합니다.
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("login_check", loginCheck)
        startActivityForResult(intent, REQUEST_MAIN_ACTIVITY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        DB = DBMember(this, "DBMEMBER", null, 1)

        loginBinding.loginBtn.setOnClickListener {
            val user = loginBinding.nickname.text.toString()
            val pass = loginBinding.password.text.toString()
            if (user == "" || pass == "") {
                Toast.makeText(this@LoginActivity, "회원 정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                val checkUserpass = DB!!.checkUserpass(user, pass)
                if (checkUserpass) {
                    Toast.makeText(this@LoginActivity, "로그인 완료!", Toast.LENGTH_SHORT).show()
                    moveToMainActivity()
                } else {
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
