package com.example.tutorial_example.Compose

import androidx.lifecycle.viewModelScope
import com.example.tutorial_example.Compose.common.base.BaseViewModel
import com.example.tutorial_example.Compose.common.state.NavigationDrawerItemData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProjectHomePageViewModel : BaseViewModel() {
    private val _drawerItemState = MutableStateFlow(NavigationDrawerItemData())
    val drawerItemState: StateFlow<NavigationDrawerItemData> = _drawerItemState.asStateFlow()

    /**
     * 模擬呼叫API獲取drawer資料
     * */
    fun getNavigationDrawerItemData() {
        viewModelScope.launch {
            _loadingState.update { true } //open mask
            delay(2000)

            val data =
                NavigationDrawerItemData(
                    drawerItems = listOf(
                        NavigationDrawerItemData.DrawerItem(
                            majorTitle = "項目1",
                            minorItems = listOf(
                                NavigationDrawerItemData.DrawerItem.MinorItemData(
                                    title = "小項目1",
                                    router = ""
                                ),
                                NavigationDrawerItemData.DrawerItem.MinorItemData(
                                    title = "小項目2",
                                    router = ""
                                ),
                                NavigationDrawerItemData.DrawerItem.MinorItemData(
                                    title = "小項目3",
                                    router = ""
                                ),
                                NavigationDrawerItemData.DrawerItem.MinorItemData(
                                    title = "小項目4",
                                    router = ""
                                ),
                                NavigationDrawerItemData.DrawerItem.MinorItemData(
                                    title = "小項目5",
                                    router = ""
                                ),)
                        ),
                        NavigationDrawerItemData.DrawerItem(
                            majorTitle = "項目2",
                        ),
                    )
                )

            _drawerItemState.update { currentState ->
                currentState.copy(
                    drawerItems = data.drawerItems
                )
            }

            _loadingState.update { false } //close mask
        }
    }
}