package com.example.tutorial_example.Compose.common.view

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import com.example.tutorial_example.Compose.ui.theme.Gray60

/** SnackBar的單純UI */
@Composable
fun SnackBar(data: SnackbarData, shape: Shape) {

    Row() {
        Button(
            onClick = { data.dismiss() },
            colors = ButtonDefaults.buttonColors(Gray60),
            shape = shape
        ) {
            Text(text = data.visuals.message)
        }
    }
}

/** 統一動畫時長與snackBar顯示時間 */
//數值來源：原始碼
fun getSnackBarHoldTime(time: SnackbarDuration): Int {
    //原時長+1s
    return when(time) {
        SnackbarDuration.Short -> { 4200 }
        SnackbarDuration.Long -> { 10000 }
        else -> { 2000 }
    }
}