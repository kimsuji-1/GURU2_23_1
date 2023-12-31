package com.example.guru2_23_1.ui.notifications

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.guru2_23_1.databinding.FragmentNotificationsBinding
import com.example.guru2_23_1.ui.DiaryActivity
import com.example.guru2_23_1.ui.MealActivity
import com.example.guru2_23_1.ui.SchelduleActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.guru2_23_1.R

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(getResources().getDrawable(R.drawable.img1))
        (activity as AppCompatActivity).supportActionBar?.elevation = 0F
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonMeal.setOnClickListener {startActivity(Intent(requireContext(), MealActivity::class.java))}
        binding.buttonMood.setOnClickListener {startActivity(Intent(requireContext(), DiaryActivity::class.java))}
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
