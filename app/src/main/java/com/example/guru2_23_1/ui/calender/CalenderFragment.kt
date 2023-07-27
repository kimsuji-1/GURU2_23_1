package com.example.guru2_23_1.ui.calender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guru2_23_1.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    val itemList = arrayListOf<Date>()
    val listAdapter = CalendarAdapter(itemList)
    lateinit var calendarList: RecyclerView
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var monthYearTv: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_calender, container, false)
        calendarList = view.findViewById(R.id.calendar_list)
        mLayoutManager = LinearLayoutManager(view.context)
        monthYearTv = view.findViewById(R.id.month_year_tv)

        // recyclerView orientation (가로 방향 스크롤 설정)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        calendarList.layoutManager = mLayoutManager

        setListView()

        // 오늘 버튼 클릭 시 CalendarMonthFragment로 전환
        val todayBtn: Button = view.findViewById(R.id.today_btn)
        todayBtn.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val calendarMonthFragment = CalendarMonthFragment()

            fragmentTransaction.replace(R.id.fragment_container, calendarMonthFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }

    // list(날짜, 요일)를 만들고, adapter를 등록하는 메소드
    private fun setListView() {
        val calendar = Calendar.getInstance()

        // 현재 달의 마지막 날짜
        val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)
        val currentYear = calendar.get(Calendar.YEAR)

        for (i: Int in 1..lastDayOfMonth) {
            calendar.set(currentYear, calendar.get(Calendar.MONTH), i)
            val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)
            itemList.add(Date(dayOfWeek, i.toString()))
        }
        calendarList.adapter = listAdapter

        // 월과 연도를 표시하는 TextView 갱신
        monthYearTv.text = "$currentMonth $currentYear"
    }
}