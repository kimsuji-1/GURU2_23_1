package com.example.guru2_23_1.ui.calender

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guru2_23_1.MemoActivity
import com.example.guru2_23_1.R
import com.example.guru2_23_1.ui.DB.DBLoader
import com.example.guru2_23_1.ui.adapter.MemoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CalenderFragment : Fragment() {

    private lateinit var adapter: MemoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calender, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAdd = view.findViewById<FloatingActionButton>(R.id.btn_add)
        btnAdd.setOnClickListener {
            startActivity(Intent(requireContext(), MemoActivity::class.java))
        }

        adapter = MemoAdapter(requireContext())
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.setList(DBLoader(requireContext()).memoList(null))
    }
}