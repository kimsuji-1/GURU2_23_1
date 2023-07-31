package com.example.guru2_23_1.ui.home

import android.content.Intent
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
import com.example.guru2_23_1.ui.LoginActivity
import com.example.guru2_23_1.ui.MealActivity
import com.example.guru2_23_1.ui.RegisterActivity

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

        val login_check = arguments?.getInt("login_check")

        if (login_check== 1 ){//로그인 O
            //로그인/회원가입 버튼 안 보이게
            binding.loginRegisterLayout.visibility = View.GONE
            //홈 멘트 보이게
            binding.txtHomemention.visibility = View.VISIBLE
        }else{//로그인 X
            //로그인/회원가입 버튼 보이게
            binding.loginRegisterLayout.visibility = View.VISIBLE
            //홈 멘트 안 보이게
            binding.txtHomemention.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = inflater.inflate(R.layout.fragment_home, container, false)
        return root

        binding.btnLogin.setOnClickListener {startActivity(Intent(requireContext(), LoginActivity::class.java))}
        binding.btnRegister.setOnClickListener {startActivity(Intent(requireContext(), RegisterActivity::class.java))}

    }
}