package com.example.guru2_23_1.ui.calender

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guru2_23_1.MemoActivity
import com.example.guru2_23_1.R
import com.example.guru2_23_1.ui.DB.DBLoader
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class CalenderFragment : Fragment() {

    private lateinit var adapter : MemoAdapter
    private var selectday = "";

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = inflater.inflate(R.layout.fragment_calender, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val btnAdd = view.findViewById<FloatingActionButton>(R.id.btn_add)
        btnAdd.setOnClickListener {
            startActivity(Intent(requireContext(), MemoActivity::class.java))
        }
        val calendarView = view.findViewById<CalendarView>(R.id.calendar_view)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = MemoAdapter(reqireContext())
        recyclerView.adapter = adapter

        calendarView.setOnDateChangeListener(object : CalendarView.onDateChangeListener{
            override fun onSelectedDayChange(p0: calendarView, p1: Int, p2: Int, p3: Int){
                if(selectday.equals(String.format("%04d%02d%02d",p1, p2+1, 3))){
                    startActivity(Intent(requireContext(), MemoActivity::class.java))
                }
                else{
                    val calendar : Calendar = Calendar.getInstance()
                    calendar.set(p1, p2, p3)
                    val day: String = calendar.timeInMillis.toString().substring(0, 6)
                    adapter.setList(DBLoader(requireContext()).memoList(day.toLong()))
                    selectday = p0.toString() + p1.toString()+ p3.toString()
                }
            }
        })
        val date = Date()
        date.time = calendarView.date
        selectday = SimpleDateFormat("yyyyMMdd").format(date)
        adapter.setList(
            DBLoader(requireContext()).memoList(
            calendarView.date.toString().substring(0, 6).toLong()
            )
        )

    }
}