package com.example.guru2_23_1.ui.calender

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.guru2_23_1.R

class CalendarAdapter(private val dataSet: ArrayList<Date>): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    var drawable: Drawable? = null
    private lateinit var itemClickListener : OnItemClickListener

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val dateTv: TextView = view.findViewById(R.id.date_cell)
        val dayTv: TextView = view.findViewById(R.id.day_cell)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.calendar_cell, viewGroup, false)

        drawable = ContextCompat.getDrawable(view.context, R.drawable.round)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dateTv.text = dataSet[position].date
        holder.dayTv.text = dataSet[position].day
    }

    override fun getItemCount() = dataSet.size
}