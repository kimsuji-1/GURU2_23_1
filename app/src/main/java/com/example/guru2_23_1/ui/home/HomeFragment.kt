package com.example.guru2_23_1.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.guru2_23_1.R
import com.example.guru2_23_1.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Home"
//        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ff2255")))
        //액션바 배경색 흰 색으로
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.white))
        (activity as AppCompatActivity).supportActionBar?.elevation = 0F // 액션바 그림자 안 보이개
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }
}