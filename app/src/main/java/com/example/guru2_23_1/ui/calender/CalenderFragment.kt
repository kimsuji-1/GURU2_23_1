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
import android.widget.LinearLayout
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
    private lateinit var eventLayout: View
    private lateinit var selectedDate: Calendar
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var plusButton: Button
    private lateinit var checkButton: Button
    private lateinit var updateButton: Button
    private var eventList: List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calender, container, false)
        dynamicTextView = view.findViewById(R.id.dynamic_text_tv)
        calendarContainer = view.findViewById(R.id.calendar_container)
        plusButton = view.findViewById(R.id.plus_btn)
        checkButton = view.findViewById(R.id.check_btn)
        updateButton = view.findViewById(R.id.update_btn)

        val monthButton = view.findViewById<Button>(R.id.Month_btn)
        val addButton = view.findViewById<Button>(R.id.add_btn)
        val DiaryrButton = view.findViewById<Button>(R.id.Diary_btn)
        val prevMonthButton = view.findViewById<Button>(R.id.prevMonthButton)
        val nextMonthButton = view.findViewById<Button>(R.id.nextMonthButton)
        val headerTextView = view?.findViewById<TextView>(R.id.headerTextView)

        // Hide the headerTextView initially (when there are no schedule items)
        headerTextView?.visibility = View.GONE

        addButton.setOnClickListener {
            calendarContainer.removeAllViews()

            monthButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            addButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            DiaryrButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

            prevMonthButton.visibility = View.GONE
            nextMonthButton.visibility = View.GONE

            val todayCalendar = Calendar.getInstance()
            currentYear = todayCalendar.get(Calendar.YEAR)
            currentMonth = todayCalendar.get(Calendar.MONTH) + 1

            dynamicTextView.text = "${currentYear}.${currentMonth}.${todayCalendar.get(Calendar.DAY_OF_MONTH)}"

            selectedDate = todayCalendar

            plusButton.visibility = View.VISIBLE
            checkButton.visibility = View.VISIBLE
            updateButton.visibility = View.VISIBLE

            // 수정된 부분: 사용자가 날짜를 선택하지 않은 경우 오늘 날짜에 대한 데이터를 표시
            showContentForDate(todayCalendar)
        }

        DiaryrButton.setOnClickListener {
            val todayCalendar = Calendar.getInstance()
            currentYear = todayCalendar.get(Calendar.YEAR)
            currentMonth = todayCalendar.get(Calendar.MONTH) + 1

            prevMonthButton.visibility = View.GONE
            nextMonthButton.visibility = View.GONE
            plusButton.visibility = View.GONE
            checkButton.visibility = View.GONE
            updateButton.visibility = View.GONE

            monthButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            addButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            DiaryrButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

            // Display only the current month and year
            dynamicTextView.text = "${currentYear}.${currentMonth}.${todayCalendar.get(Calendar.DAY_OF_MONTH)}"
            // Clear the calendar container
            calendarContainer.removeAllViews()

            // Clear the schedule list
            val scheduleListLayout = view?.findViewById<LinearLayout>(R.id.schedule_list)
            scheduleListLayout?.removeAllViews()

            // Hide the ToDo list
            val todoLayout = view?.findViewById<LinearLayout>(R.id.todo_layout)
            todoLayout?.visibility = View.GONE
            headerTextView?.visibility = View.GONE
        }

        monthButton.setOnClickListener {
            resetAddButtonLayout()

            val todayCalendar = Calendar.getInstance()
            currentYear = todayCalendar.get(Calendar.YEAR)
            currentMonth = todayCalendar.get(Calendar.MONTH) + 1

            monthButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            addButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            DiaryrButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

            prevMonthButton.visibility = View.VISIBLE
            nextMonthButton.visibility = View.VISIBLE

            // Show the schedule list for the selected date
            showScheduleForDate(selectedDate)
            // Since it's the month view, hide the ToDo list for the selected date
            drawCalendar(currentYear, currentMonth)
            hideToDoForDate()
        }

        plusButton.setOnClickListener {
            // "추가" 버튼을 누르면 다이얼로그를 보여주는 함수 호출
            showAddEventDialogInHomeFragment(selectedDate)
        }

        checkButton.setOnClickListener {
            // Call the function to show the content for the selected date
            showContentForDate(selectedDate)
        }

        prevMonthButton.setOnClickListener {
            currentMonth -= 1
            if (currentMonth < Calendar.JANUARY) {
                currentYear -= 1
                currentMonth = Calendar.DECEMBER
            }
            drawCalendar(currentYear, currentMonth)
        }

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

        prevMonthButton.visibility = View.GONE
        nextMonthButton.visibility = View.GONE

        selectedDate = Calendar.getInstance()

        return view
    }

    private fun drawCalendar(year: Int, month: Int) {
        calendarContainer.removeAllViews()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
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
        // DatePickerDialog를 생성할 때 현재 월 - 1 (0부터 시작하는 인덱스)을 전달합니다.
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerStyle,
            { _, year, month, dayOfMonth ->
                selectedDate.set(year, month, dayOfMonth)
                // 선택한 월에 1을 더하여 표시될 월 값을 조정합니다. (0부터 시작하는 인덱스이기 때문에)
                val selectedYear = selectedDate.get(Calendar.YEAR)
                val selectedMonth = selectedDate.get(Calendar.MONTH) + 1 // 월 값에 1을 더합니다.
                val selectedDay = selectedDate.get(Calendar.DAY_OF_MONTH)
                dynamicTextView.text = "${selectedYear}.${selectedMonth}.${selectedDay}"
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH), // 현재 월 - 1 (0부터 시작하는 인덱스)을 전달합니다.
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
        // 선택한 날짜로 SQLite DB에서 해당 날짜에 저장된 이벤트를 가져옵니다.
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val selectedDateString = dateFormat.format(selectedDate.time)

        // DBHelper를 사용하여 해당 날짜의 이벤트를 가져옵니다.
        val dbHelper = DBCALENDAR(requireContext())
        val eventList = dbHelper.getEventsForDate(selectedDateString)

        // 가져온 이벤트를 캘린더 아래에 표시합니다.
        val dateTextView = TextView(requireContext())
        dateTextView.text = selectedDateString

        // 기존에 있는 날짜 데이터를 모두 제거
        calendarContainer.removeAllViews()

        // 선택한 날짜와 해당 날짜의 이벤트 데이터를 새로 표시
        calendarContainer.addView(dateTextView)

        // 스케줄 목록을 표시하는 LinearLayout
        val scheduleLayout = view?.findViewById<LinearLayout>(R.id.schedule_layout)
        scheduleLayout?.visibility = View.VISIBLE

        // 스케줄 리스트를 표시하는 LinearLayout
        val scheduleListLayout = view?.findViewById<LinearLayout>(R.id.schedule_list)
        scheduleListLayout?.removeAllViews()

        // Separate events into "Schedule" and "ToDo" items
        val scheduleList = mutableListOf<String>()
        val todoList = mutableListOf<String>()

        for (event in eventList) {
            if (event.startsWith("Schedule:")) {
                scheduleList.add(event.substringAfter("Schedule:").trim())
            } else if (event.startsWith("ToDo:")) {
                todoList.add(event.substringAfter("ToDo:").trim())
            }
        }

        // Display Schedule items
        if (scheduleList.isNotEmpty()) {
            for (event in scheduleList) {
                val eventTextView = TextView(requireContext())
                eventTextView.text = event
                scheduleListLayout?.addView(eventTextView)
            }
        } else {
            val noEventTextView = TextView(requireContext())
            noEventTextView.text = "No schedule for this date."
            scheduleListLayout?.addView(noEventTextView)
        }

        val headerTextView = view?.findViewById<TextView>(R.id.headerTextView)
        headerTextView?.visibility = if (scheduleList.isEmpty()) View.GONE else View.VISIBLE

        // Hide ToDo items
        val todoLayout = view?.findViewById<LinearLayout>(R.id.todo_layout)
        todoLayout?.visibility = View.GONE
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
            setBackgroundResource(R.drawable.rounded_corner) // 노란색으로 적용되는 이미지 리소스
            setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        // 클릭한 날짜를 selectedDay 변수에 저장
        selectedDay = clickedDayTextView

        // 선택한 날짜에 해당하는 스케줄 리스트를 가져옴
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month - 1, day)
        eventList = getEventsForDate(selectedDate)

        // 스케줄 리스트를 표시하는 부분만 보여줌
        showOnlyScheduleList()
    }

    private fun getEventsForDate(selectedDate: Calendar): List<String> {
        // 선택한 날짜로 SQLite DB에서 해당 날짜에 저장된 이벤트를 가져옴
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val selectedDateString = dateFormat.format(selectedDate.time)

        // DBHelper를 사용하여 해당 날짜의 이벤트를 가져옴
        val dbHelper = DBCALENDAR(requireContext())
        return dbHelper.getEventsForDate(selectedDateString)
    }

    private fun showOnlyScheduleList() {
        // 스케줄 목록을 표시하는 LinearLayout
        val scheduleLayout = view?.findViewById<LinearLayout>(R.id.schedule_layout)
        scheduleLayout?.visibility = View.VISIBLE

        // 스케줄 리스트를 표시하는 LinearLayout
        val scheduleListLayout = view?.findViewById<LinearLayout>(R.id.schedule_list)
        scheduleListLayout?.removeAllViews()

        if (eventList.isNotEmpty()) { // 스케줄이 있는 경우에만 처리
            // 이벤트가 있는 경우 스케줄 목록에 추가
            for (event in eventList) {
                if (event.startsWith("Schedule:")) {
                    val eventTextView = TextView(requireContext())
                    eventTextView.text = event.substringAfter("Schedule:").trim()
                    scheduleListLayout?.addView(eventTextView)
                }
            }
        } else {
            // 스케줄이 없는 경우 빈 TextView를 추가하여 아무 내용도 표시하지 않음
            val noEventTextView = TextView(requireContext())
            noEventTextView.text = "" // 빈 텍스트로 설정
            scheduleListLayout?.addView(noEventTextView)
        }

        // 투두 목록을 숨김
        val todoLayout = view?.findViewById<LinearLayout>(R.id.todo_layout)
        todoLayout?.visibility = View.GONE
    }

    private fun showScheduleForDate(selectedDate: Calendar) {
        // 선택한 날짜로 SQLite DB에서 해당 날짜에 저장된 이벤트를 가져옵니다.
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val selectedDateString = dateFormat.format(selectedDate.time)

        // DBHelper를 사용하여 해당 날짜의 이벤트를 가져옵니다.
        val dbHelper = DBCALENDAR(requireContext())
        val eventList = dbHelper.getEventsForDate(selectedDateString)

        // 기존에 있는 날짜 데이터를 모두 제거
        calendarContainer.removeAllViews()

        // 선택한 날짜와 해당 날짜의 이벤트 데이터를 새로 표시
        val dateTextView = TextView(requireContext())
        dateTextView.text = selectedDateString
        calendarContainer.addView(dateTextView)

        // Only show the schedule list
        val scheduleListLayout = view?.findViewById<LinearLayout>(R.id.schedule_list)
        scheduleListLayout?.removeAllViews()

        if (eventList.isNotEmpty()) { // 스케줄이 있는 경우에만 처리
            // 이벤트가 있는 경우 스케줄 목록에 추가
            for (event in eventList) {
                if (event.startsWith("Schedule:")) {
                    val eventTextView = TextView(requireContext())
                    eventTextView.text = event.substringAfter("Schedule:").trim()
                    scheduleListLayout?.addView(eventTextView)
                }
            }
        } else {
            // 스케줄이 없는 경우 빈 TextView를 추가하여 아무 내용도 표시하지 않음
            val noEventTextView = TextView(requireContext())
            noEventTextView.text = "No schedule for this date."
            scheduleListLayout?.addView(noEventTextView)
        }

        // Hide ToDo list
        hideToDoForDate()
    }
    private fun hideToDoForDate() {
        // 투두 목록을 표시하는 LinearLayout
        val todoLayout = view?.findViewById<LinearLayout>(R.id.todo_layout)
        todoLayout?.visibility = View.GONE

        // 투두 리스트를 표시하는 LinearLayout
        val todoListLayout = view?.findViewById<LinearLayout>(R.id.todo_list)
        todoListLayout?.removeAllViews()
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

    private fun resetAddButtonLayout() {
        val contentEditText = view?.findViewById<EditText>(R.id.contentEditText)
        contentEditText?.text?.clear()

        val addedContentTextView = view?.findViewById<TextView>(R.id.addedContentTextView)
        addedContentTextView?.visibility = View.GONE
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