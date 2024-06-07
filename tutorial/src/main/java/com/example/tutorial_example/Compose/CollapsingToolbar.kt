package com.example.tutorial_example.Compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ListItemDefaults.Elevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CollapsingToolbar(
    progress: Float,
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "")
        }
    }
}