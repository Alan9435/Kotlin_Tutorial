package com.example.tutorial_example.Compose.common.view

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tutorial_example.Compose.common.modifier.setHomeDropDownMenu
import com.example.tutorial_example.Compose.common.state.ScaffoldState
import com.example.tutorial_example.Compose.ui.theme.Green_00BB00
import com.example.tutorial_example.R
import ir.kaaveh.sdpcompose.sdp

/**
 * 自定義toolbar
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MyCenterTopAppBar(
    appbarState: ScaffoldState = ScaffoldState(),
    onNavigationIconClick: () -> Unit = {}
) {
    if (appbarState.appBarIsShow) {
        var showMenu by remember { mutableStateOf(false) }

        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = { onNavigationIconClick() }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "menu", modifier = Modifier.padding(8.sdp))
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Green_00BB00,
                titleContentColor = Color.White,
            ),
            actions = {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_person_24),
                        contentDescription = null,
                        modifier = Modifier
                            .height(24.sdp)
                            .width(24.sdp)
                    )
                }

                MyDropDownMenu(showMenu = showMenu) {
                    showMenu = false
                }
            },
            title = {
                Text(text = appbarState.title)
            }
        )
    }
}

/** Menu抽屜 */
@Composable
fun MyDropDownMenu(showMenu: Boolean, onDismissRequest: () -> Unit) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { onDismissRequest() },
        modifier = Modifier.setHomeDropDownMenu()
    ) {
        DropdownMenuItem(
            onClick = {
                //do something
            }, text = {
                Text(text = "item1")
            })
        DropdownMenuItem(
            onClick = {
                //do something
            }, text = {
                Text(text = "item2")
            })
    }
}