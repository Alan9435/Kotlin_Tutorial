package com.example.tutorial_example.Fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.common.Base.BaseFragment
import com.example.common.Base.Extensins.toSafeString
import com.example.common.Base.Util.DashboardIntent
import com.example.tutorial_example.ViewModel.MVIViewModel
import com.example.tutorial_example.databinding.FragmentMviBinding
import kotlinx.coroutines.launch

/**
 * MVI structure
 * 練習MVI架構
 * */
class MVIFragment: BaseFragment<FragmentMviBinding>() {
    private val viewModel by viewModels<MVIViewModel>()
    override fun onViewInit() {
        observeData()
        init()
    }

    private fun init() {
        // MVI 中的intent 行為
        binding.btnIntent.setOnClickListener {
            viewModel.sendIntent(DashboardIntent.GetQuote)
        }
    }

    /**
     * 處理 viewModel 資料顯示給用戶
     * 建議每個case 拉一個function 便於測試及修改
     * */
    private fun  observeData() {
        lifecycleScope.launch {
            viewModel.state.collect { dashboardState ->
                when(dashboardState) {
                    is MVIViewModel.DashboardState.idle -> {
                        Log.d("********", "Idle")
                    }

                    is MVIViewModel.DashboardState.Error -> {
                        binding.tvCount.text = dashboardState.error
                    }
                    is MVIViewModel.DashboardState.Success -> {
                        binding.tvCount.text = dashboardState.quote?.totalCount.toSafeString()
                    }
                }
            }
        }
    }
}