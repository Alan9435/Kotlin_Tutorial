package com.example.tutorial.Fragment

import com.example.common.Base.BaseFragment
import com.example.common.Base.InterfaceCallback
import com.example.common.Base.TestDataClass
import com.example.tutorial.databinding.FragmentInterfaceCallbackBinding

class InterfaceCallbackFragment: BaseFragment<FragmentInterfaceCallbackBinding>(),InterfaceCallback {
    override fun onViewInit() {

    }

    override fun callBackTest(testDataClass: TestDataClass) {

    }

}