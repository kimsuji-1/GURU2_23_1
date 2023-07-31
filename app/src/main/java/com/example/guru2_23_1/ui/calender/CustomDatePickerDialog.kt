package com.example.guru2_23_1.ui.calender

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.annotation.StyleRes
import com.example.guru2_23_1.R

class CustomDatePickerDialog(
    context: Context,
    @StyleRes themeResId: Int,
    private val listener: DatePickerDialog.OnDateSetListener,
    year: Int,
    month: Int,
    dayOfMonth: Int
) : DatePickerDialog(context, themeResId, listener, year, month, dayOfMonth) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 헤더의 날짜 선택 부분의 색상을 노란색으로 변경
        val dayPicker = datePicker.findViewById<DatePicker>(context.resources.getIdentifier("android:id/day", null, null))
        val monthPicker = datePicker.findViewById<DatePicker>(context.resources.getIdentifier("android:id/month", null, null))
        val yearPicker = datePicker.findViewById<DatePicker>(context.resources.getIdentifier("android:id/year", null, null))

        dayPicker?.let {
            it.setBackgroundColor(context.resources.getColor(R.color.yellow)) // 여기서 R.color.yellow는 미리 정의된 노란색 색상 리소스입니다.
        }

        monthPicker?.let {
            it.setBackgroundColor(context.resources.getColor(R.color.yellow))
        }

        yearPicker?.let {
            it.setBackgroundColor(context.resources.getColor(R.color.yellow))
        }
    }
}