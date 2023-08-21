package com.example.tutorial_example.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bot.ibank.presentation.common.utility.SingleMutableLiveData
import com.example.common.Base.DataClass.QuoteList
import com.example.common.Base.InterfaceApi.InterfaceApiService
import com.example.tutorial_example.Fragment.ConnectAPIFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ConnectAPIViewModel : ViewModel() {
    private val _apiData = SingleMutableLiveData<QuoteList>()
    val apiData: SingleMutableLiveData<QuoteList>
        get() = _apiData

    private val _isCoverOpen = SingleMutableLiveData<Boolean>()
    val isCoverOpen: SingleMutableLiveData<Boolean>
        get() = _isCoverOpen

    fun getQuote() {
        //獲取物件實例
        val quotesApi = ConnectAPIFragment.RetrofitHelper.getInstance()
            .create(InterfaceApiService.QuotesApi::class.java)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    //模擬遮罩
                    _isCoverOpen.postValue(true)
                }
                try {
                    val result = quotesApi.getQuotes()
                    _apiData.postValue(result?.body())
                } catch (e: Exception) {
                    Log.d("*****", "error : $e")
                }
                withContext(Dispatchers.Main) {
                    //模擬遮罩結束
                    _isCoverOpen.postValue(false)
                }
            }
        }
    }
}