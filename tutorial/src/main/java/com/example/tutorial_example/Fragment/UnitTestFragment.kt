package com.example.tutorial_example.Fragment

import com.example.common.Base.BaseFragment
import com.example.tutorial_example.databinding.FragmentUnitTestBinding

class UnitTestFragment : BaseFragment<FragmentUnitTestBinding>() {
    override fun onViewInit() {

        binding.btnRegister.setOnClickListener {
            val loginAccount = binding.etAccount.text.toString()
            val pwd = binding.etPwd.text.toString()

//            result = UnitTestUtils().isLoginAccountVerify(loginAccount = loginAccount)
//            ...
        }
    }

    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun reverseString(input: String): String {

        return input.reversed()
    }

    //viewModel的test
}