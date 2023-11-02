package com.example.tutorial_example.Compose

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.tutorial_example.Compose.common.modifier.setLoginButton
import com.example.tutorial_example.Compose.common.modifier.setLoginPageEditText
import com.example.tutorial_example.Compose.common.route.RouteManager.Companion.LOGINCONTENT
import com.example.tutorial_example.Compose.common.route.RouteManager.Companion.LOGINPAGE
import com.example.tutorial_example.Compose.common.view.BaseAlertDialog
import com.example.tutorial_example.Compose.common.view.ButtonCompose
import com.example.tutorial_example.Compose.common.view.EditTextCompose
import com.example.tutorial_example.Compose.common.view.LoadingMask
import com.example.tutorial_example.Compose.common.view.SecretCheckBox
import com.example.tutorial_example.Compose.ui.theme.Gray20
import com.example.tutorial_example.Compose.ui.theme.Gray80
import com.example.tutorial_example.R

class ProjectTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            MyAppNavHost(navController = navController, startDestination = LOGINCONTENT)
        }
    }

    @Composable
    fun MyAppNavHost(
        navController: NavHostController = rememberNavController(),
        startDestination: String = LOGINCONTENT
    ) {
        val context = LocalContext.current

        NavHost(navController = navController, startDestination = startDestination) {
            loginGraph(navController = navController, context)
            //增添home ,page01,page02
        }
    }

    private fun NavGraphBuilder.loginGraph(navController: NavController, context: Context) {
        navigation(startDestination = LOGINPAGE, route = LOGINCONTENT) {
            composable(route = LOGINPAGE) {
                LoginPage()
            }
        }
    }

    //todo 可以把login的區塊在獨立出來成新的 Composable
    @Preview
    @Composable
    private fun LoginPage(projectViewModel: ProjectTestViewModel = viewModel()) {
        Log.d("render", "LoginPage: render")

        //collectAsState() 從stateFlow收集值 只要有新值發布至stateFlow 就會更新
        val loginUiState by projectViewModel.uiState.collectAsState()
        val loadingState by projectViewModel.loadingState.collectAsState()

        var acct by rememberSaveable { mutableStateOf("") }
        var pwd by rememberSaveable { mutableStateOf("") }
        var isShowAcct by rememberSaveable { mutableStateOf(true) }
        var isShowPwd by rememberSaveable { mutableStateOf(true) }

        //當前的LocalFocusManager實例 操作頁面上的focus
        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier
                .background(color = Gray20)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //帳號輸入
            EditTextCompose(
                modifier = Modifier.setLoginPageEditText(),
                defaultValue = acct,
                placeholder = { Text(text = getString(R.string.compose_input_acct), color = Gray80) },
                trailingIcon = {
                    SecretCheckBox(checked = isShowAcct) { isChecked ->
                        isShowAcct = isChecked
                    }
                },
                showPwd = isShowAcct,
                onValueChange = { value ->
                    if (value.matches(Regex("^[a-zA-Z0-9]*$")) && value.length <= 10) {
                        acct = value
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password, //僅顯示英文與數字鍵盤 但無法擋控 ",!@#$%^" 等符號
                    imeAction = ImeAction.Next
                )
            )
            //密碼輸入
            EditTextCompose(
                modifier = Modifier.setLoginPageEditText(),
                defaultValue = pwd,
                placeholder = { Text(text = getString(R.string.compose_input_pwd), color = Gray80) },
                trailingIcon = {
                    SecretCheckBox(checked = isShowPwd) { isChecked ->
                        isShowPwd = isChecked
                    }
                },
                showPwd = isShowPwd,
                onValueChange = { value ->
                    //輸入限制
                    if (value.matches(Regex("^[a-zA-Z0-9]*$")) && value.length <= 10) {
                        pwd = value
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password, //僅顯示英文與數字鍵盤 但無法擋控 ",!@#$%^" 等符號
                    imeAction = ImeAction.Done //設定鍵盤選項=完成
                ),
                //複寫鍵盤動作
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }  //clear focus
                )
            )
            //登入按鈕
            ButtonCompose(
                onClick = {
                    //todo 成功->導航
                    projectViewModel.checkLoginInput(acct = acct, pwd = pwd)
                },
                buttonText = getString(R.string.compose_login),
                buttonTextModifier = Modifier.setLoginButton()
            )
        }

        //todo 有必要用function包dialog 達到重複利用 或 直接放置2個dialog compose?
        loginUiState.errorState?.let {
            //顯示錯誤訊息用
            if (it.dialogFlag) {
                BaseAlertDialog(
                    msg = getString(it.errorMsgRes),
                    onConfirmClick = {
                        projectViewModel.closeDialog()
                    },
                )
            }
        }

        if (loadingState) {
            LoadingMask()
        }
    }
}