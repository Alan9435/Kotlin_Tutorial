package com.example.tutorial_example.Compose

import androidx.lifecycle.viewModelScope
import com.example.common.Base.Extensins.isNotNull
import com.example.tutorial_example.Compose.common.base.BaseViewModel
import com.example.tutorial_example.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProjectTestViewModel : BaseViewModel() {
    //asStateFlow -> 將可變的（MutableStateFlow）轉換成不可變的（StateFlow）
    //有助於保持狀態的一致性和不可變性
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun checkLoginInput(acct: String, pwd: String) {
        //檢核輸入
        val checkResult = when {
            acct.isEmpty() -> R.string.compose_input_acct_empty
            acct.length < 6 -> R.string.compose_input_acct_length
            pwd.isEmpty() -> R.string.compose_input_pwd_empty
            pwd.length < 6 -> R.string.compose_input_pwd_length
            else -> null
        }

        if (checkResult.isNotNull()) {
            //使用update 修改狀態
            _uiState.update { currentState -> //currentState表示當前狀態
                currentState.copy(  //複製一個當前狀態 並進行修改
                    errorState = checkResult?.let {
                        LoginUiState.ErrorState(
                            errorMsgRes = it,
                            dialogFlag = true
                        )
                    }
                )
            }
        } else {
            callApi()
        }
    }

    /** 模擬呼叫Api */
    private fun callApi() {
        //遮罩
        viewModelScope.launch {
            _loadingState.update { true } //open mask
            delay(2000)

            //理論上呼叫api統一包裝至baseViewModel控管 onSuccess and onError
            if (true) { //模擬成功呼叫
                _uiState.update { currentState -> //currentState表示當前狀態
                    currentState.copy(
                        //複製一個當前狀態 並進行修改
                        loginResponse = LoginUiState.LoginResponseData(
                            title = "API RS測試標題",
                            userName = "Alan",
                            somethingFlag = 1
                        ),
                    )
                }
            } else {
                _uiState.update { currentState -> //currentState表示當前狀態
                    currentState.copy(  //複製一個當前狀態 並進行修改
                        errorState =
                        LoginUiState.ErrorState(
                            errorMsgRes = R.string.compose_network_error,
                            dialogFlag = true
                        )
                    )
                }
            }
            _loadingState.update { false } //close mask
        }
    }

    fun closeDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                errorState =
                LoginUiState.ErrorState(
                    dialogFlag = false
                )
            )
        }
    }
}

/** Login頁的UI狀態資料 */
data class LoginUiState(
    val loginResponse: LoginResponseData? = null,
    val errorState: ErrorState? = null,
) {
    /** 模擬api回傳的data格式 */
    data class LoginResponseData(
        val title: String = "",
        val userName: String = "",
        val somethingFlag: Int = 0,
    )

    /** 錯誤時的data*/
    data class ErrorState(
        val errorMsgRes: Int = -404,
        val dialogFlag: Boolean = false
    )
}