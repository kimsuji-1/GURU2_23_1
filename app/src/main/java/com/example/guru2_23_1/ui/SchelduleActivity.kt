package com.example.guru2_23_1.ui

import android.graphics.Paint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2_23_1.R

class SchelduleActivity : AppCompatActivity() {

    private lateinit var todoList: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var todoEdit: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        //Array 초기화
        todoList = ArrayList()

        //ArrayAdapter 초기화 (context, layout, list)
        adapter = ArrayAdapter(this, R.layout.activity_schedule_list_item, todoList)

        //UI 객체 생성
        val listView: ListView = findViewById(R.id.list_view)
        val add_btn: Button = findViewById(R.id.add_btn)
        todoEdit = findViewById(R.id.todo_edt)

        //Adapter 적용
        listView.adapter = adapter

        //버튼 이벤트
        add_btn.setOnClickListener {
            //할 일 추가
            addItem()
        }

        //리스트 아이템 클릭 이벤트
        listView.setOnItemClickListener { adapterView, view, i, I ->

            val textView: TextView = view as TextView

            //취소선 넣기
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
    }//OnCreate

    private fun addItem(){

        //입력값 변수에 담기
        val todo: String = todoEdit.text.toString()

        //값이 비워있지 않다면
        if (todo.isNotEmpty()){
            //할 일 추가
            todoList.add(todo)

            //적용
            adapter.notifyDataSetChanged()
        }else{
            Toast.makeText(this, "할 일을 적어주세요", Toast.LENGTH_SHORT).show()
        }
    }
}