package com.example.tutorial_example.Compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import ir.kaaveh.sdpcompose.sdp


@Preview
@Composable
fun AnimalCard(

) {
    Card(
        modifier = Modifier.aspectRatio(0.66f),
        shape = RoundedCornerShape(8.sdp)
    ) {
        Box {
            AsyncImage(model = "https://images.pexels.com/photos/460775/pexels-photo-460775.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", contentDescription = "")
        }
    }
}