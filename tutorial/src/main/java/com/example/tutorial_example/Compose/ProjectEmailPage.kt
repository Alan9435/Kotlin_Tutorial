package com.example.tutorial_example.Compose

import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tutorial_example.Compose.common.base.EnableBackPressed
import com.example.tutorial_example.Compose.common.view.ProductLazyRow
import ir.kaaveh.sdpcompose.sdp

@Preview
@Composable
fun EmailPage() {
    EnableBackPressed {
        Text(
            modifier = Modifier.offset(0.sdp, (-30).sdp),
            text = "email"
        )
    }
}