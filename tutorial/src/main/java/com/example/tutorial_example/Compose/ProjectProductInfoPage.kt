package com.example.tutorial_example.Compose

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import com.example.tutorial_example.Compose.common.model.ProductDetailData
import com.example.tutorial_example.Compose.ui.theme.Purple40
import ir.kaaveh.sdpcompose.sdp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductInfoPage(context: Context, infoData: ProductDetailData) {
    //todo 詳細瞭解原理 https://medium.com/androiddevelopers/understanding-nested-scrolling-in-jetpack-compose-eb57c1ea0af0
    val AppBarHeight = 56.sdp

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val appBarMaxHeightPx = with(LocalDensity.current) { AppBarHeight.roundToPx() }
        val connection = remember(appBarMaxHeightPx) {
            CollapsingAppBarNestedScrollConnection(appBarMaxHeightPx)
        }

        val density = LocalDensity.current
        val spaceHeight by remember(density) {
            derivedStateOf {
                with(density) {
                    (appBarMaxHeightPx + connection.appBarOffset).toDp()
                }
            }
        }

        Box(Modifier.nestedScroll(connection = connection)) {
            Column {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(top = AppBarHeight)
                ) {
                    items(100) {
                        Text(modifier = Modifier.padding(20.sdp), text = "test")
                    }
                }
            }

            TopAppBar(
                modifier = Modifier.offset { IntOffset(0, connection.appBarOffset) },
                title = { Text(text = "Jetpack Compose") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Purple40,
                    titleContentColor = Color.White
                )
            )
        }
    }
//
//    Column(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        AsyncImage(
//            model = "https://images.pexels.com/photos/460775/pexels-photo-460775.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
//            contentDescription = ""
//        )
//
//        Box(modifier = Modifier
//            .fillMaxWidth()
//            .padding(12.sdp)) {
//            Text(
//                fontSize = 15.ssp,
//                textAlign = TextAlign.Start,
//                text = context.getString(R.string.compose_product_name, infoData.productName)
//            )
//            Text(
//                modifier = Modifier.align(alignment = Alignment.BottomEnd),
//                fontSize = 13.ssp,
//                textAlign = TextAlign.End,
//                text = context.getString(R.string.compose_product_price, infoData.productPrice)
//            )
//        }

//    }

    fun Modifier.nestedScroll(
        connection: NestedScrollConnection,
        dispatcher: NestedScrollDispatcher? = null
    ): Modifier {
        return this
    }
}

private class CollapsingAppBarNestedScrollConnection(
    val appBarMaxHeight: Int
) : NestedScrollConnection {

    var appBarOffset: Int by mutableIntStateOf(0)
        private set

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val delta = available.y.toInt()
        val newOffset = appBarOffset + delta
        val previousOffset = appBarOffset
        appBarOffset = newOffset.coerceIn(-appBarMaxHeight, 0)
        val consumed = appBarOffset - previousOffset
        return Offset(0f, consumed.toFloat())
    }
}
