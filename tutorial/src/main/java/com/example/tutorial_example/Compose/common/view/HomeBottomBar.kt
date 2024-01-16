package com.example.tutorial_example.Compose.common.view

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.tutorial_example.Compose.common.route.RouteManager
import com.example.tutorial_example.Compose.common.state.BottomNavigationItem
import com.example.tutorial_example.R

@Composable
fun HomeBottomBar(navController: NavController,context: Context, pageRouter: String, onSelectItem: (String,String) -> Unit = {_,_ -> }) {
    //bottomBar 項目
    val items = listOf(
        BottomNavigationItem(
            title = context.getString(R.string.compose_setting_title),
            selectedIcon = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings,
            router = RouteManager.SettingPage.routeName
        ),
        BottomNavigationItem(
            title = context.getString(R.string.compose_home_title),
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            router = RouteManager.HomePage.routeName
        ),
        BottomNavigationItem(
            title = context.getString(R.string.compose_msg_title),
            selectedIcon = Icons.Filled.Email,
            unSelectedIcon = Icons.Outlined.Email,
            router = RouteManager.EmailPage.routeName
        ),
    )

    //compose bottomBar 元件
    NavigationBar {
        items.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                selected = bottomNavigationItem.router == pageRouter,
                onClick = {
                    onSelectItem.invoke(bottomNavigationItem.router, bottomNavigationItem.title)
                },
                label = {
                    Text(text = bottomNavigationItem.title)
                },
                icon = {
                    Icon(
                        imageVector = if (bottomNavigationItem.router == pageRouter) {
                            bottomNavigationItem.selectedIcon
                        } else {
                            bottomNavigationItem.unSelectedIcon
                        },
                        contentDescription = bottomNavigationItem.title
                    )
                })
        }
    }
}