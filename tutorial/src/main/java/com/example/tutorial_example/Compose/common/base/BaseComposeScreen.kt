package com.example.tutorial_example.Compose.common.base

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.tutorial_example.Compose.common.route.RouteManager
import com.example.tutorial_example.R

/** screen基底 = baseFragment概念*/
@Composable
fun BaseComposeScreen(content: @Composable () -> Unit) {
    content()
}

/** 禁止用戶滑動倒退*/
@Composable
fun EnableBackPressed(content: @Composable () -> Unit) {
    BackHandler {
        Log.d("*****", "onBackHandler")
    }
    content()
}

/**
 * 覆寫 onBackPressed 防止回上頁
 * 滑動2次後離開app */
//enabled = false 無禁用
@Composable
fun OnBackPressedLeave(context: Context,content: @Composable () -> Unit) {
    var backCount by rememberSaveable {
        mutableStateOf(0)
    }

    BackHandler {
        backCount ++
        when(backCount) {
            //android 12 開始 系統需要知道是哪個app發出的toast 所以icon不可變更 (跟隨app icon)
            1 -> Toast.makeText(context, context.getString(R.string.compose_toast_finish_app), Toast.LENGTH_SHORT).show()  //提醒再一次會退出
            2 -> (context as Activity).finish() //關閉app 但本例非單一activity
        }
    }
    //screen
    content()
}