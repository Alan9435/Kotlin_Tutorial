package com.example.tutorial_example.Compose

import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.tutorial_example.Compose.common.base.OnBackPressedLeave
import com.example.tutorial_example.Compose.common.model.HomeProductList
import com.example.tutorial_example.Compose.common.model.ProductCellData
import com.example.tutorial_example.Compose.common.model.ProductDetailData
import com.example.tutorial_example.Compose.common.route.RouteManager
import com.example.tutorial_example.Compose.common.route.RouteManager.EmailPage
import com.example.tutorial_example.Compose.common.route.RouteManager.HomeContainer
import com.example.tutorial_example.Compose.common.route.RouteManager.HomePage
import com.example.tutorial_example.Compose.common.route.RouteManager.SettingPage
import com.example.tutorial_example.Compose.common.state.ScaffoldState
import com.example.tutorial_example.Compose.common.view.CarouselPager
import com.example.tutorial_example.Compose.common.view.CarouselPagerItemState
import com.example.tutorial_example.Compose.common.view.FloatActionButton
import com.example.tutorial_example.Compose.common.view.HomeBottomBar
import com.example.tutorial_example.Compose.common.view.LoadingMask
import com.example.tutorial_example.Compose.common.view.MyCenterTopAppBar
import com.example.tutorial_example.Compose.common.view.MyDrawerSheet
import com.example.tutorial_example.Compose.common.view.NavigationDrawerItems
import com.example.tutorial_example.Compose.common.view.ProductLazyRow
import com.example.tutorial_example.Compose.common.view.SnackBar
import com.example.tutorial_example.Compose.common.view.getSnackBarHoldTime
import com.example.tutorial_example.R
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.launch

private var snackHoldTime = SnackbarDuration.Short

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.homeGraph(navController: NavController, context: Context) {
    navigation(
        startDestination = HomePage.routeName,
        route = HomeContainer.getRoute(),
        arguments = listOf(
            navArgument(HomeContainer.value) {
                type = LoginDataParamType()
            }
        )
    ) {
        composable(route = HomePage.routeName) {
            val homePageViewModel: ProjectHomePageViewModel = viewModel()

            val data = it.arguments?.serializable<LoginUiState.LoginResponseData>(HomeContainer.value)

            //如果要跳到特定頁 要接router參數後設為預設
            var pageRouter by rememberSaveable {
                mutableStateOf(HomePage.routeName)
            }

            //控制scaffold 的狀態
            var scaffoldState by rememberSaveable {
                mutableStateOf(ScaffoldState(title = context.getString(R.string.compose_home_title)))
            }

            //控制drawer 展開/關閉
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            //drawer 內容資料
            val drawerData by homePageViewModel.drawerItemState.collectAsState()

            //loading
            val loadingState by homePageViewModel.loadingState.collectAsState()

            //控制snackbar狀態
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            var isStart by remember {
                mutableStateOf(false)
            }

            val holdTime = getSnackBarHoldTime(snackHoldTime)

            //給snackBar 設置形狀變化
            val shapeCorner by animateDpAsState(
                targetValue = if (isStart) 10.sdp else 30.sdp,
                animationSpec = tween(durationMillis = holdTime),
            ) {
                //動畫結束時
                isStart = false
            }

            val shape = RoundedCornerShape(shapeCorner)

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    MyDrawerSheet {
                        NavigationDrawerItems(drawerData)
                    }
                },
                gesturesEnabled = true //使否允許拖移開啟
            ) {
                Scaffold(
                    topBar = {
                        MyCenterTopAppBar(scaffoldState) {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatActionButton {
                            isStart = true

                            //啟動協程 受到LaunchedEffect管理
                            scope.launch {
                                snackbarHostState.apply {
                                    currentSnackbarData?.dismiss()
                                    showSnackbar("SnackBar msg", "label", true, snackHoldTime)
                                }
                            }
                        }
                    },
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            snackbar = { data ->
                                SnackBar(data, shape)
                            })
                    },
                    bottomBar = {
                        HomeBottomBar(navController = navController, context = context, pageRouter = pageRouter) { router, topBarTitle ->
                            scaffoldState = ScaffoldState(
                                title = topBarTitle
                            )
                            pageRouter = router
                        }
                    }
                ) { padding ->
                    //content 放screen用 提供padding參數調整content畫面
                    Box(modifier = Modifier.padding(padding)) {
                        BaseHomePage(data = data, pageState = pageRouter, navController = navController, context = context)
                    }
                }

                if (loadingState) {
                    LoadingMask()
                }
            }

            LaunchedEffect(Unit) {
                if(drawerData.drawerItems.isEmpty()) {
                    //獲取drawer 資料
                    homePageViewModel.getNavigationDrawerItemData()
                }
            }
        }
    }
}

//由toolbar homeContent bottomBar組成
@Composable
fun BaseHomePage(data: LoginUiState.LoginResponseData?, pageState: String, navController: NavController, context: Context) {
    when (pageState) {
        HomePage.routeName -> HomePage(loginStateData = data, navController = navController, context = context)
        SettingPage.routeName -> SettingPage(navController = navController)
        EmailPage.routeName -> EmailPage()
    }
}

@Composable
fun HomePage(loginStateData: LoginUiState.LoginResponseData?, navController: NavController, context: Context) {
    //通常來源為API
    val data = listOf(
        CarouselPagerItemState(
            icon = Icons.Default.DateRange,
        ),
        CarouselPagerItemState(
            icon = Icons.Default.Build,
        ),
        CarouselPagerItemState(
            icon = Icons.Default.Share,
        ),
    )


    val homeProductData = HomeProductList(
        productList = listOf(
            ProductCellData(
                icon = "https://images.pexels.com/photos/460775/pexels-photo-460775.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                detailData = ProductDetailData(
                    productName = "測試商品1",
                    productNumber = 9527,
                    productPrice = "127",
                )
            ),
            ProductCellData(
                icon = "https://images.pexels.com/photos/460775/pexels-photo-460775.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                detailData = ProductDetailData(
                    productName = "測試商品2",
                    productNumber = 27149,
                    productPrice = "389",
                )
            ),
            ProductCellData(
                icon = "https://images.pexels.com/photos/460775/pexels-photo-460775.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                detailData = ProductDetailData(
                    productName = "測試商品3",
                    productNumber = 55688,
                    productPrice = "28",
                )
            ),
            ProductCellData(
                icon = "https://images.pexels.com/photos/460775/pexels-photo-460775.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                detailData = ProductDetailData(
                    productName = "測試商品4",
                    productNumber = 4427,
                    productPrice = "124",
                )
            ),
            ProductCellData(
                icon = "https://images.pexels.com/photos/460775/pexels-photo-460775.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                detailData = ProductDetailData(
                    productName = "測試商品5",
                    productNumber = 1090,
                    productPrice = "5940",
                )
            ),
        )
    )

    OnBackPressedLeave(context = context) {
        Column {
            CarouselPager(data = data)
            ProductLazyRow(dataList = homeProductData.productList, navController = navController)
        }
    }
}