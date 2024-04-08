package com.example.tutorial_example.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bot.ibank.presentation.common.utility.SingleMutableLiveData
import com.example.common.Base.DataClass.QuoteList
import com.example.common.Base.InterfaceApi.InterfaceApiService
import com.example.common.Base.Util.DashboardIntent
import com.example.tutorial_example.Fragment.ConnectAPIFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Model in MVI
 * */
class MVIViewModel: ViewModel() {

    private val _state = MutableStateFlow<DashboardState>(DashboardState.idle)
    val state: StateFlow<DashboardState>
        get() = _state

    /**
     * View 狀態表
     * */
    sealed class DashboardState {
        data object idle : DashboardState()
        data class Success(val quote: QuoteList?) : DashboardState()
        data class Error(val error: String?) : DashboardState()
    }

    /**
     * 根據使用者傳遞的intent
     * 執行對應的商業邏輯
     * */
    fun sendIntent(intent: DashboardIntent) {
        when(intent) {
            is DashboardIntent.GetQuote -> {
                getQuote()
            }
            // other case
        }
    }

    private fun getQuote() {
        //獲取物件實例
        val quotesApi = ConnectAPIFragment.RetrofitHelper.getInstance()
            .create(InterfaceApiService.QuotesApi::class.java)

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = quotesApi.getQuotes()
                    _state.value = DashboardState.Success(result?.body())
                } catch (e: Exception) {
                    DashboardState.Error(e.localizedMessage)
                }
            }
        }
    }
}