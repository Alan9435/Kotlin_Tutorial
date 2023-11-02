package com.example.tutorial_example.Compose.common.base

import androidx.lifecycle.ViewModel
import com.example.tutorial_example.Compose.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel: ViewModel() {
    //loading State 統一管理loading狀態
    protected val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()
}