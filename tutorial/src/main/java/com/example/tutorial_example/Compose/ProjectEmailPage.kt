package com.example.tutorial_example.Compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tutorial_example.Compose.common.base.EnableBackPressed

@Preview
@Composable
fun EmailPage() {
    EnableBackPressed {
        Text(text = "email")
    }
}