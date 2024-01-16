package com.example.tutorial_example.Compose

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.tutorial_example.Compose.common.base.EnableBackPressed
import com.example.tutorial_example.Compose.common.route.RouteManager

@Composable
fun SettingPage(navController: NavController) {
    EnableBackPressed {
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "setting")
        }
    }
}