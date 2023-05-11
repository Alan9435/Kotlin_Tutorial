package com.example.tutorial.Fragment

import androidx.navigation.fragment.findNavController
import com.example.common.Base.BaseFragment
import com.example.tutorial.R
import com.example.tutorial.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    override fun onViewInit() {
        binding.btnService.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_navigation_service_music)
        }

        binding.btnApiTest.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_navigation_connect_api)
        }
    }
}