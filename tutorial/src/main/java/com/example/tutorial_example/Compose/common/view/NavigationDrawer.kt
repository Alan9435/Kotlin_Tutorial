package com.example.tutorial_example.Compose.common.view

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tutorial_example.Compose.common.modifier.setModalNavigationDrawer
import com.example.tutorial_example.Compose.common.state.NavigationDrawerItemData
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

/**
 * 側邊欄內容
 * 通常資料來源為API
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawerItems(data: NavigationDrawerItemData) {
    Log.d("*******", "NavigationDrawerItems: ${data}")
    //改成搜尋匡 過濾
    Text("Drawer title", modifier = Modifier.padding(16.sdp))
    Divider()
    Column() {
        data.drawerItems.forEach { drawerItem ->
            DrawerItem(drawerItem)
            Divider()
        }
    }


//    NavigationDrawerItem(
//        label = { Text(text = "Drawer Item") },
//        selected = true,
//        onClick = { /*TODO*/ }
//    )
}

/** 側邊欄最小寬度*/
private val MinimumDrawerWidth = 240.dp

/**
 * 自定義側邊欄
 * 為了自定義modifier樣式
 * 直接複製 ModalDrawerSheet內的樣式後添加
 * */
@ExperimentalMaterial3Api
@Composable
fun MyDrawerSheet(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = DrawerDefaults.windowInsets,
    drawerShape: Shape = DrawerDefaults.shape,
    drawerContainerColor: Color = MaterialTheme.colorScheme.surface,
    drawerContentColor: Color = contentColorFor(drawerContainerColor),
    drawerTonalElevation: Dp = DrawerDefaults.ModalDrawerElevation,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .sizeIn(
                minWidth = MinimumDrawerWidth,
                maxWidth = DrawerDefaults.MaximumDrawerWidth
            )
            .setModalNavigationDrawer()
            .fillMaxHeight(),
        shape = drawerShape,
        color = drawerContainerColor,
        contentColor = drawerContentColor,
        tonalElevation = drawerTonalElevation
    ) {
        Column(
            Modifier
                .sizeIn(
                    minWidth = MinimumDrawerWidth,
                    maxWidth = DrawerDefaults.MaximumDrawerWidth
                )
                .windowInsetsPadding(windowInsets),
            content = content
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun DrawerItem(
    drawerItem: NavigationDrawerItemData.DrawerItem = NavigationDrawerItemData.DrawerItem()
) {
    var itemExtend by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(150f)
    ) {
        //字體拉大 間隔條開
        //大項
        Box(
            modifier = Modifier
                .padding(8.sdp)
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember {
                        //用於追蹤事件的物件
                        MutableInteractionSource()
                    }
                ) {
                    //空的表示有子項目 不需導航
                    if (drawerItem.majorRouter.isEmpty()) {
                        itemExtend = !itemExtend
                    } else {
                        //導航至 drawerItem.majorRouter 預計設為deepLink
                    }
                },
        ) {
            Text(text = drawerItem.majorTitle, fontSize = 20.ssp, modifier = Modifier.align(Alignment.Center))
            if (drawerItem.minorItems.isNotEmpty()) {
                Icon(
                    imageVector = if (itemExtend) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 10.sdp)
                        .size(24.sdp)
                        .align(Alignment.CenterEnd)
                )
            }
        }
        Divider()

        //詳細效果參考：https://developer.android.com/jetpack/compose/animation/composables-modifiers?hl=zh-tw#animatedvisibility
        AnimatedVisibility(
            visible = itemExtend, //判斷顯示/消失的狀態
//            enter = scaleIn(), //顯示時的動畫效果
//            exit = scaleOut()  //消失時的動畫效果
        ) {
            //小項
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                drawerItem.minorItems.forEach { data ->
                    TextButton(
                        modifier = Modifier.padding(4.sdp),
                        onClick = {
                            //導航至 data.router 預計用deepLink
                        }
                    ) {
                        Text(text = data.title, fontSize = 15.ssp)
                    }
                }
            }
        }
    }
}