package com.example.guru2_23_1.ui.calender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guru2_23_1.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale

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
            val fragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            val calendarMonthFragment = CalendarMonthFragment()

            // FragmentContainer에 CalendarMonthFragment 추가
            fragmentTransaction.replace(R.id.fragment_container, calendarMonthFragment)

            // 뒤로가기 버튼으로 이전 Fragment로 돌아갈 수 있도록 스택에 추가
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }


    // list(날짜, 요일)를 만들고, adapter를 등록하는 메소드
    private fun setListView() {
        // 현재 달의 마지막 날짜
        val lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).dayOfMonth
        val currentMonth = LocalDate.now().month.getDisplayName(TextStyle.FULL, Locale.US)
        val currentYear = LocalDate.now().year

        for (i: Int in 1..lastDayOfMonth) {
            val date = LocalDate.of(LocalDate.now().year, LocalDate.now().month, i)
            val dayOfWeek: DayOfWeek = date.dayOfWeek
            val dayOfWeekShort = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US)

            itemList.add(Date(dayOfWeekShort, i.toString()))
        }
        calendarList.adapter = listAdapter

        // 월과 연도를 표시하는 TextView 갱신
        monthYearTv.text = "$currentMonth $currentYear"
    }
}

