package com.example.guru2_23_1.ui.calender

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.guru2_23_1.R
import com.example.guru2_23_1.ui.DB.DBCALENDAR
import java.text.SimpleDateFormat
import java.util.*

class CalenderFragment : Fragment() {
    private lateinit var dynamicTextView: TextView
    private lateinit var calendarContainer: GridLayout
    private var currentYear = 2023
    private var currentMonth = Calendar.JULY
    private var selectedDay: TextView? = null
    private lateinit var addEventButton: ImageButton
    private lateinit var eventLayout: View
    private lateinit var selectedDate: Calendar // 선택된 날짜를 저장할 변수
    private lateinit var datePickerDialog: DatePickerDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calender, container, false)
        dynamicTextView = view.findViewById(R.id.dynamic_text_tv)
        addEventButton = view.findViewById(R.id.add_event_btn)
        calendarContainer = view.findViewById(R.id.calendar_container)

        val todayButton = view.findViewById<Button>(R.id.today_btn)
        val addButton = view.findViewById<Button>(R.id.add_btn)
        val monthyearButton = view.findViewById<Button>(R.id.month_year_tv)
        val prevMonthButton = view.findViewById<Button>(R.id.prevMonthButton)
        val nextMonthButton = view.findViewById<Button>(R.id.nextMonthButton)

        // 'ADD TASK' 버튼 클릭 리스너 설정
        addButton.setOnClickListener {
            dynamicTextView.text = "Add Task 화면이 선택되었습니다"
            calendarContainer.removeAllViews() // 달력이 그려진 부분 삭제

            todayButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            addButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            monthyearButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

            prevMonthButton.visibility = View.GONE
            nextMonthButton.visibility = View.GONE

            val todayCalendar = Calendar.getInstance()
            currentYear = todayCalendar.get(Calendar.YEAR)
            currentMonth = todayCalendar.get(Calendar.MONTH) + 1 // 오늘의 월을 동적으로 설정

            // 현재 날짜로 dynamicTextView를 업데이트
            dynamicTextView.text = "${currentYear}.${currentMonth}.${todayCalendar.get(Calendar.DAY_OF_MONTH)}"

            // 'ADD TASK' 버튼을 클릭하면 오늘 날짜가 선택되도록 설정
            selectedDate = todayCalendar

            // 'ADD TASK' 버튼 클릭 시 add_event_btn을 보이도록 설정
            addEventButton.visibility = View.VISIBLE
        }

        // 'addEventButton' 클릭 이벤트 리스너에서 함수 이름 변경
        addEventButton.setOnClickListener {
            showAddEventDialogInHomeFragment(selectedDate)
        }

        // 'month_year_tv' 버튼 클릭 리스너 설정
        monthyearButton.setOnClickListener {
            calendarContainer.removeAllViews()
            val todayCalendar = Calendar.getInstance()

            currentYear = todayCalendar.get(Calendar.YEAR)
            currentMonth = todayCalendar.get(Calendar.MONTH) + 1 // 오늘의 월을 동적으로 설정

            prevMonthButton.visibility = View.GONE
            nextMonthButton.visibility = View.GONE
            addEventButton.visibility = View.GONE

            todayButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            addButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            monthyearButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        // 'today_btn' 클릭 리스너 설정
        todayButton.setOnClickListener {
            val todayCalendar = Calendar.getInstance()
            currentYear = todayCalendar.get(Calendar.YEAR)
            currentMonth = todayCalendar.get(Calendar.MONTH) + 1 // 오늘의 월을 동적으로 설정

            // 달력을 다시 그리도록 수정
            drawCalendar(currentYear, currentMonth)

            prevMonthButton.visibility = View.VISIBLE
            nextMonthButton.visibility = View.VISIBLE
            addEventButton.visibility = View.GONE

            todayButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            addButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            monthyearButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        }

        // '이전 달' 버튼 클릭 리스너 설정
        prevMonthButton.setOnClickListener {
            currentMonth -= 1
            if (currentMonth < Calendar.JANUARY) {
                currentYear -= 1
                currentMonth = Calendar.DECEMBER
            }
            drawCalendar(currentYear, currentMonth)
        }

        // '다음 달' 버튼 클릭 리스너 설정
        nextMonthButton.setOnClickListener {
            currentMonth += 1
            if (currentMonth > Calendar.DECEMBER) {
                currentYear += 1
                currentMonth = Calendar.JANUARY
            }
            drawCalendar(currentYear, currentMonth)
        }

        dynamicTextView.setOnClickListener {
            showDatePickerDialog()
        }
        // 초기에는 달력을 그리지 않음
        prevMonthButton.visibility = View.GONE
        nextMonthButton.visibility = View.GONE

        selectedDate = Calendar.getInstance()

        return view
    }

    private fun drawCalendar(year: Int, month: Int) {
        calendarContainer.removeAllViews() // 기존에 그려진 내용 삭제

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1) // 수정: currentMonth는 Calendar 클래스의 월 값을 따르므로 1을 빼줌
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        dynamicTextView.text = "${calendar.get(Calendar.YEAR)}.${month}"

        val weekDays = arrayOf("일", "월", "화", "수", "목", "금", "토")

        // 요일 헤더를 출력
        for (weekDay in weekDays) {
            val weekDayTextView = TextView(requireContext())
            weekDayTextView.text = weekDay
            weekDayTextView.gravity = Gravity.CENTER
            weekDayTextView.setPadding(0, 16, 0, 16)
            calendarContainer.addView(weekDayTextView)

            val params = weekDayTextView.layoutParams as GridLayout.LayoutParams
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)

            weekDayTextView.layoutParams = params
        }

        // 이전 달의 마지막 날짜와 해당 요일 구하기
        val prevCalendar = Calendar.getInstance()
        prevCalendar.set(Calendar.YEAR, year)
        prevCalendar.set(Calendar.MONTH, month - 1) // 이전 달로 설정
        prevCalendar.set(Calendar.DAY_OF_MONTH, 1)

        val lastDayOfPrevMonth = prevCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val lastDayOfWeekPrevMonth = prevCalendar.get(Calendar.DAY_OF_WEEK)

        // 이전 달의 날짜 출력
        var dayOfWeek = lastDayOfWeekPrevMonth - 1
        for (i in 0 until dayOfWeek) {
            // 이전 달의 날짜들은 출력하지 않음
            val prevMonthDayTextView = TextView(requireContext())
            prevMonthDayTextView.text = ""
            calendarContainer.addView(prevMonthDayTextView)

            val params = prevMonthDayTextView.layoutParams as GridLayout.LayoutParams
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            params.columnSpec = GridLayout.spec(i, 1f)

            prevMonthDayTextView.layoutParams = params
        }

        // 날짜 출력
        dayOfWeek = lastDayOfWeekPrevMonth - 1 // 이전 달의 날짜부터 출력
        for (day in 1..daysInMonth) {
            val dayTextView = TextView(requireContext())
            dayTextView.text = day.toString()
            dayTextView.gravity = Gravity.CENTER // 중앙 정렬
            dayTextView.setPadding(0, 16, 0, 16) // 날짜 텍스트뷰의 상하 간격 추가

            // 이전 달과 다음 달의 날짜는 클릭 이벤트를 설정하지 않음
            if (dayOfWeek < 0 || dayOfWeek >= 7) {
                dayTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            } else {
                dayTextView.setOnClickListener { onDateClicked(year, month, day) } // 날짜 클릭 이벤트 리스너 설정

                // 토요일과 일요일인 경우에는 텍스트 색상을 파란색과 빨간색으로 설정
                val currentDayOfWeek = (dayOfWeek + 1) % 7
                if (currentDayOfWeek == Calendar.SATURDAY) {
                    dayTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                } else if (currentDayOfWeek == Calendar.SUNDAY) {
                    dayTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                } else {
                    // 나머지 요일들은 기본 색상으로 설정 (이 예시에서는 검은색)
                    dayTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }
            }

            calendarContainer.addView(dayTextView)
            val params = dayTextView.layoutParams as GridLayout.LayoutParams
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) // 각 날짜를 표시하는 줄 간격을 조정
            params.columnSpec = GridLayout.spec(dayOfWeek, 1f)

            dayTextView.layoutParams = params

            // 요일 증가 및 다음 날짜로 이동
            dayOfWeek = (dayOfWeek + 1) % 7
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerStyle,
            { _, year, month, dayOfMonth ->
                selectedDate.set(year, month, dayOfMonth)
                // 선택한 날짜를 dynamicTextView에 업데이트
                val selectedYear = selectedDate.get(Calendar.YEAR)
                val selectedMonth = selectedDate.get(Calendar.MONTH) + 1 // 월에 1을 더해 정확한 월로 표시
                val selectedDay = selectedDate.get(Calendar.DAY_OF_MONTH)
                dynamicTextView.text = "${selectedYear}.${selectedMonth}.${selectedDay}"
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )

        // 최소 날짜를 현재 날짜로 설정
        datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis

        // DatePickerDialog 보이기
        datePickerDialog.show()

        // "확인" 버튼의 텍스트 색상을 검은색으로 변경
        val okButtonId = resources.getIdentifier("android:id/button1", null, null)
        val okButton = datePickerDialog.findViewById<Button>(okButtonId)
        okButton?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        // "취소" 버튼의 텍스트 색상을 검은색으로 변경
        val cancelButtonId = resources.getIdentifier("android:id/button2", null, null)
        val cancelButton = datePickerDialog.findViewById<Button>(cancelButtonId)
        cancelButton?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }

    private fun showAddEventDialogInHomeFragment(selectedDate: Calendar) {
        // 선택한 날짜 정보를 받아옴
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val selectedDateString = dateFormat.format(selectedDate.time)

        // 다이얼로그를 위한 custom layout
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_event, null)
        val typeRadioGroup = dialogView.findViewById<RadioGroup>(R.id.typeRadioGroup)
        val contentEditText = dialogView.findViewById<EditText>(R.id.contentEditText)
        val addButton = dialogView.findViewById<Button>(R.id.addButton)
        val addedContentTextView = dialogView.findViewById<TextView>(R.id.addedContentTextView)

        // 다이얼로그 생성
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Schedule, ToDo 추가")
            .setView(dialogView)

        val alertDialog = builder.create()
        alertDialog.show()

        // '추가' 버튼 클릭 시 이벤트 처리
        addButton.setOnClickListener {
            val type = if (typeRadioGroup.checkedRadioButtonId == R.id.scheduleRadioButton) {
                "Schedule"
            } else {
                "ToDo"
            }

            val content = contentEditText.text.toString()

            // DBHelper를 사용하여 일정 또는 투두 리스트를 SQLite DB에 추가
            val dbHelper = DBCALENDAR(requireContext())
            dbHelper.addEventToDatabase(selectedDateString, type, content)

            // 추가한 내용을 TextView에 업데이트
            addedContentTextView.text = "$type: $content"
            addedContentTextView.visibility = View.VISIBLE

            // 다이얼로그 닫기 (또는 이 부분을 원하는 타이밍에 닫으세요)
            alertDialog.dismiss()

            // 저장된 내용을 해당 날짜 아래에 보여주는 함수 호출
            showContentForDate(selectedDate)
        }
    }

    private fun addEventToDatabaseInHomeFragment(selectedDate: String, type: String, content: String) {
        // DBHelper를 사용하여 데이터베이스에 해당 날짜의 일정 또는 투두를 추가
        val dbHelper = DBCALENDAR(requireContext())
        dbHelper.addEventToDatabase(selectedDate, type, content)
    }

    private fun showContentForDate(selectedDate: Calendar) {
        // selectedDate로 SQLite DB에서 해당 날짜에 저장된 내용을 가져옴
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val selectedDateString = dateFormat.format(selectedDate.time)

        // DBHelper를 사용하여 해당 날짜의 내용을 가져옴
        val dbHelper = DBCALENDAR(requireContext())
        val eventList = dbHelper.getEventsForDate(selectedDateString)

        // 가져온 내용을 해당 날짜 아래에 보여주는 부분
        val dateTextView = TextView(requireContext())
        dateTextView.text = selectedDateString

        val eventsTextView = TextView(requireContext())
        eventsTextView.text = eventList.joinToString("\n")

        calendarContainer.addView(dateTextView)
        calendarContainer.addView(eventsTextView)

        // TextView 스타일을 적절히 수정하세요 (크기, 색상, 여백 등)
    }

    private fun onDateClicked(year: Int, month: Int, day: Int) {
        val clickedDayTextView = getDayTextViewByDate(day)

        // 기존에 선택된 날짜가 있는 경우 노란색 그림자 제거
        selectedDay?.apply {
            background = null
            setTextColor(getDayTextColorByDayOfWeek(this))
        }

        // 새로 클릭한 날짜에 노란색 그림자 적용
        clickedDayTextView.apply {
            setBackgroundResource(R.drawable.rounded_corner)
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        // 클릭한 날짜를 selectedDay 변수에 저장
        selectedDay = clickedDayTextView
    }

    private fun getDayTextViewByDate(day: Int): TextView {
        for (i in 0 until calendarContainer.childCount) {
            val childView = calendarContainer.getChildAt(i) as? TextView
            if (childView != null) {
                val currentDay = childView.text.toString().toIntOrNull()
                if (currentDay == day) {
                    return childView
                }
            }
        }
        // 해당하는 날짜의 TextView를 찾지 못한 경우 기본적으로 빈 TextView를 반환
        return TextView(requireContext())
    }

    private fun getDayTextColorByDayOfWeek(dayTextView: TextView): Int {
        val currentDayOfWeek = (calendarContainer.indexOfChild(dayTextView) + 1) % 7
        return when (currentDayOfWeek) {
            Calendar.SATURDAY -> ContextCompat.getColor(requireContext(), R.color.blue)
            Calendar.SUNDAY -> ContextCompat.getColor(requireContext(), R.color.red)
            else -> ContextCompat.getColor(requireContext(), R.color.black)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CalenderFragment()
    }
}