package com.example.guru2_23_1.ui.calender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guru2_23_1.R

class CalendarMonthFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // calendar_month.xml 레이아웃을 inflate하여 뷰를 생성합니다.
        return inflater.inflate(R.layout.calendar_month, container, false)
    }
}
