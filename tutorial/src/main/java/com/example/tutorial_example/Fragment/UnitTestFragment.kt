package com.example.tutorial_example.Fragment

import android.widget.Toast
import com.example.common.Base.BaseFragment
import com.example.tutorial_example.databinding.FragmentUnitTestBinding
import com.example.tutorial_example.utils.UnitTestUtils

class UnitTestFragment : BaseFragment<FragmentUnitTestBinding>() {
    override fun onViewInit() {

        binding.btnRegister.setOnClickListener {

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