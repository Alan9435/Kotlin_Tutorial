package com.example.tutorial_example.Compose.common.state

import android.os.Parcelable
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ScaffoldState(
    val appBarIsShow: Boolean = true,
    val title: String = "",
): Parcelable

data class BottomNavigationItem(
    val index: Int = 0,
    val title: String = "",
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val router: String
)

@Parcelize
data class NavigationDrawerItemData(
    val drawerItems: @RawValue List<DrawerItem> = listOf()
): Parcelable {
    data class DrawerItem(
        val majorTitle: String = "",
        val majorRouter: String = "",
        val minorItems: @RawValue List<MinorItemData> = listOf()
    ) {
        data class MinorItemData(
            val title: String = "",
            val router: String = ""
        )
    }
}