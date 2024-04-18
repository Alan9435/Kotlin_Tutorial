package com.example.tutorial_example.Fragment

import com.example.common.Base.BaseFragment
import com.example.tutorial_example.databinding.FragmentUnitTestBinding

class UnitTestFragment: BaseFragment<FragmentUnitTestBinding>() {
    override fun onViewInit() {

    }

    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun reverseString(input: String): String {

        return input.reversed()
    }

    //viewModel的test
}