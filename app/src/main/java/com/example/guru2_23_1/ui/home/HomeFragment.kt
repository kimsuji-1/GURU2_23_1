package com.example.guru2_23_1.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guru2_23_1.databinding.FragmentHomeBinding
import com.example.guru2_23_1.ui.LoginActivity
import com.example.guru2_23_1.ui.RegisterActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var login_check: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // login_check 값을 받아오기 (arguments로부터)
        login_check = arguments?.getInt("login_check")

        // 로그로 login_check 값 확인
        Log.d("HomeFragment", "login_check: $login_check")

        if (login_check == 1) {
            binding.loginRegisterLayout.visibility = View.GONE
            binding.txtHomemention.visibility = View.VISIBLE
        } else {
            binding.loginRegisterLayout.visibility = View.VISIBLE
            binding.txtHomemention.visibility = View.GONE
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(requireContext(), RegisterActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUEST_MAIN_ACTIVITY = 1001
    }
}
