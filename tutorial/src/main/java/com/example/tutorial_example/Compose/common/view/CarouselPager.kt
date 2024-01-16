package com.example.tutorial_example.Compose.common.view

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.example.tutorial_example.Compose.ui.theme.Green_CEFFCE
import com.google.android.material.animation.AnimationUtils.lerp
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay

/**
 * 輪播元件
 * */
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun CarouselPager(data: List<CarouselPagerItemState> = listOf()) {
    val pagerState = rememberPagerState(initialPage = 0)

    //輪播捲動循環
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(
                if (pagerState.currentPage == data.size - 1) {
                    0
                } else {
                    pagerState.currentPage + 1
                }
            )
        }
    }

    Box(
        modifier = Modifier.background(color = Green_CEFFCE),
        contentAlignment = Alignment.BottomCenter
    ) {
        //上層圖片
        HorizontalPager(
            modifier = Modifier,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.sdp, vertical = 8.sdp),
            pageSpacing = 16.sdp,
            pageCount = data.size
        ) { page ->
            Icon(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.sdp))
                    .fillMaxWidth()
                    .size(130.sdp)
                    .background(Color.White)
                    .padding(12.sdp),
                imageVector = data[page].icon,
                contentDescription = "",
            )
        }

        //下層輪播的...
        DotIndicators(
            pageCount = data.size,
            pagerState = pagerState
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DotIndicators(
    pagerState: PagerState = rememberPagerState(),
    pageCount: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.Black else Color.Gray

            Box(
                modifier = Modifier
                    .padding(start = 3.sdp, end = 3.sdp, bottom = 10.sdp)
                    .size(12.sdp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

data class CarouselPagerItemState(
    val icon: ImageVector,
    val router: String = "" //點擊後導航用
)