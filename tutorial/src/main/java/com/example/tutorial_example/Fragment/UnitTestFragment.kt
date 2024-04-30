package com.example.tutorial_example.Fragment

import androidx.compose.material3.contentColorFor
import androidx.navigation.fragment.findNavController
import com.example.common.Base.BaseFragment
import com.example.common.Base.CommonDialog
import com.example.tutorial_example.R
import com.example.tutorial_example.databinding.FragmentUnitTestBinding
import com.example.tutorial_example.utils.UnitTestUtils

//僅為了示範方便，通常邏輯判斷寫在 viewModel
class UnitTestFragment : BaseFragment<FragmentUnitTestBinding>() {
    private var verifySuccess = false
    private val util = UnitTestUtils()
    private var commonDialog: CommonDialog? = null

    override fun onViewInit() {
        commonDialog = CommonDialog(activity)

        binding.btnRegister.setOnClickListener {
            verifySuccess =
                util.isLoginAccountVerify(binding.etAccount.text.toString()) && util.isLoginPwdVerify(
                    binding.etPwd.text.toString()
                )

            if (verifySuccess) {
                findNavController().navigate(R.id.action_unitTestFragment_to_unitTestPage2Fragment)
            } else {
                commonDialog?.showDialog(content = context?.getString(R.string.unittest_register_fail))
            }
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