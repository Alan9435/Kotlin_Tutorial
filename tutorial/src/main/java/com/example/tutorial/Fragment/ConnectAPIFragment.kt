package com.example.tutorial.Fragment

import android.annotation.SuppressLint
import android.transition.Explode
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.example.common.Base.BaseFragment
import com.example.common.Base.DataClass.QuoteList
import com.example.common.Base.Extensins.isBoolean
import com.example.common.Base.Extensins.toSafeString
import com.example.common.Base.InterfaceApi.InterfaceApiService
import com.example.tutorial.ViewModel.ConnectAPIViewModel
import com.example.tutorial.databinding.FragmentConnectApiBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConnectAPIFragment : BaseFragment<FragmentConnectApiBinding>() {

    //需要封裝
    object RetrofitHelper {
        val baseUrl = "https://quotable.io/"

        fun getInstance(): Retrofit {
            return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()) //使用Gson轉換json物件為java物件
                .build()
        }
    }

    private val viewModel by viewModels<ConnectAPIViewModel>()

    override fun onViewInit() {
        viewModelInit()

        binding.btnCallApi.setOnClickListener {
            viewModel.getQuote()
        }
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun viewModelInit() {
        viewModel.apply {
            /**
             * 設為viewLifecycleOwner 將Fragment的生命週期進行關聯
             * 確保ob隨著Fragment銷毀而註銷*/
            apiData.observe(viewLifecycleOwner) {
                binding.tvBody.text =
                    "Count： ${it?.count.toSafeString()} \n" +
                            "totalCount: ${it?.totalCount.toSafeString()} \n" +
                            "page: ${it?.page.toSafeString()} \n" +
                            "totalPages: ${it?.totalPages.toSafeString()} \n " +
                            "result: ${it?.results?.get(0)?._id}"
            }
            isCoverOpen.observe(viewLifecycleOwner){
                binding.tvCover.text = if(it.isBoolean()) "遮罩開啟" else "遮罩結束"
            }
        }
    }
}

