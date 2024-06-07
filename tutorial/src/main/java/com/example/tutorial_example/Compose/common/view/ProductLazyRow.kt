package com.example.tutorial_example.Compose.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.tutorial_example.Compose.common.model.ProductCellData
import com.example.tutorial_example.Compose.common.route.RouteManager
import com.example.tutorial_example.Compose.enCodeUri
import com.example.tutorial_example.Compose.ui.theme.Blue20
import com.example.tutorial_example.R
import ir.kaaveh.sdpcompose.sdp


@Preview
@Composable
fun ProductLazyRow(
    dataList: List<ProductCellData> = listOf(),
    navController: NavController = rememberNavController()
) {
    LazyRow {
        items(dataList) { item ->
            Column(
                modifier = Modifier
                    .size(height = 100.sdp, width = 100.sdp)
                    .background(Blue20)
                    .padding(12.sdp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.sdp))
                        .clickable {
                            item.detailData?.let { detailData ->
                                navController.navigate(
                                    RouteManager.ProductDetailPage.setSender(
                                        enCodeUri(detailData).toString()
                                    )
                                )
                            }
                        },
                    contentScale = ContentScale.Crop,
                    model = item.icon,
                    error = painterResource(id = R.drawable.hedgehog), // placeHolder
                    contentDescription = null
                )
            }
        }
    }
}