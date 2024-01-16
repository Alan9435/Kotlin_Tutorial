package com.example.tutorial_example.Compose.common.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import com.example.tutorial_example.Compose.common.appbarHeight
import com.example.tutorial_example.Compose.ui.theme.Green_CEFFCE
import ir.kaaveh.sdpcompose.sdp


fun Modifier.setHomeDropDownMenu(): Modifier = composed {
    this.background(color = Green_CEFFCE)
}