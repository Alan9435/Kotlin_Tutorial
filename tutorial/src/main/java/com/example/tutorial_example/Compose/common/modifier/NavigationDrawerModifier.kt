package com.example.tutorial_example.Compose.common.modifier

import android.util.DisplayMetrics
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import com.example.tutorial_example.Compose.ui.theme.Green_CEFFCE
import ir.kaaveh.sdpcompose.sdp

val displayMetrics = DisplayMetrics()
var width = displayMetrics.widthPixels

fun Modifier.setModalNavigationDrawer(): Modifier = composed {
    this.width((width/2).sdp)
}

fun Modifier.setDrawerItem(): Modifier = composed {
    this.background(color = Color.White)
}