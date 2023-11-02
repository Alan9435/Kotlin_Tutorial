package com.example.tutorial_example.Compose

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.example.tutorial_example.Compose.ui.theme.Pink40
import com.example.tutorial_example.Compose.ui.theme.Purple80
import com.example.tutorial_example.Compose.ui.theme.TutorialTheme
import com.example.tutorial_example.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/** 參考: https://developer.android.com/jetpack/compose/navigation?hl=zh-tw
 * Activity for test*/
class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            TutorialTheme {
//                 A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyAppNavHost(navController = navController, startDestination = "home")
                }
            }
        }
    }
}

/** 官方建議封裝
 *  MyAppNavHost作為NavController執行個體的單一可靠資料來源*/
@Composable
fun MyAppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = startDestination) {
        val id = "testId"
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            "https://www.alan9435.com?$id?name=alan".toUri(),
            context,
            ComposeActivity::class.java
        )
        val baseUri = "https://www.alan9435.com"
        val data = enCodeUri(TestData("123test", 1))

        composable(route = "home") {
            RowAlignmentBottom(onClick = {
                /** 帶值跳頁*/
//                navController.navigate("page1")
//                navController.navigate(
//                    route = "page2/$data"
//                ) {
//                    launchSingleTop = true
//                }
                /** deepLink */
                val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                    addNextIntentWithParentStack(deepLinkIntent)
                    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                }
                deepLinkPendingIntent?.send()
            })
        }
        composable(
            route = "page1",
            deepLinks = listOf(navDeepLink {
                uriPattern = "$baseUri?{id}?{name}"
            })
        ) { backStackEntry ->
            Greeting("page1 ${backStackEntry.arguments?.getString("id")} , ${backStackEntry.arguments?.getString("name")}")
        }

        composable(
            route = "page2/{testValue}?{testValue2}",
            arguments = listOf(
                navArgument("testValue") {
                    type = TestDataParamType()
                },
                navArgument("testValue2") {
                    type = NavType.StringType
                    defaultValue = "test def"
                }
            )) { backStackEntry ->
            Row {
                backStackEntry.arguments?.serializable<TestData>("testValue")?.let { Greeting(it.title) }
                backStackEntry.arguments?.getString("testValue2")?.let { Greeting(it) }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Log.d("*****", "re greeting ")
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/**
 *showBackground	是否顯示背景（預設為false）（true = 白背景）	Boolean
 *backgroundColor	設定背景顏色（須先開啟showBackground）	Long (ex: 0xff332255)
 *name	預覽圖的名稱	String
 *group	對預覽分組 （左上可以選擇分組檢視）	String
 *apiLevel	模擬不同api運行時的畫面	Int (minSdk~targetSdk)
 *widthDp	這個component裝在不同大小區塊時的情景	Int
 *heightDp	這個component裝在不同大小區塊時的情景	Int
 *locale	設定語系	String (ex: en)
 *fontScale	字體比例	Float
 *showSystemUi	是否顯示手機狀態欄（整個手機畫面）（預設false）	Boolean
 *uiMode	顯示深色模式等…	Configuration 內的常量
 *device	測試裝置	Device內的常量 (ex: Device.NEXUS_7) */
@Preview(
    name = "測試",
    group = "t1",
    apiLevel = 32,
    widthDp = 120,
    heightDp = 123,
    showBackground = true,
    backgroundColor = 0xff332255,
    locale = "en",
    fontScale = 1f,
//    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = Devices.PIXEL,
)
@Composable
fun GreetingPreview() {
    TutorialTheme {
        Greeting(stringResource(id = R.string.app_name))
    }
}

@Preview(
    name = "測試",
    group = "t2",
    showBackground = true,
    backgroundColor = 0xffffff55,
)
@Composable
fun GreetingPreviewTest() {
    TutorialTheme {
        Greeting("Android")
    }
}

@Preview
@Composable
fun RowAlignmentBottom(onClick: () -> Unit = {}) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

    val shapeCorner by animateDpAsState(
        targetValue = if (selectedTabIndex == 0) 18.sdp else 50.sdp,
        animationSpec = tween(durationMillis = 1000)
    )
    val shape = RoundedCornerShape(shapeCorner)

    val backgroundShape by animateColorAsState(
        targetValue = if (selectedTabIndex == 0) Purple80 else Pink40,
        animationSpec = tween(durationMillis = 1000)
    )

    Column(
        horizontalAlignment = Alignment.Start, // <<<
        modifier = Modifier
            .background(color = Color.DarkGray)
//            .height(dimensionResource(id = com.intuit.sdp.R.dimen._150sdp))
            .padding(5.sdp)
            .border(3.sdp, Color.Blue)
            .padding(15.sdp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(color = backgroundShape, shape = shape)
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 0.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onClick.invoke() },
                modifier = Modifier
                    .setBtnModifier()
            ) {
                Text(
                    text = "button $selectedTabIndex", fontSize = 25.ssp,
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                )
            }
        }

        //讓她點btn 跳到對應
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.padding(0.dp, 10.sdp, 0.dp, 10.sdp)
        ) {
            Button(onClick = { selectedTabIndex = 0 }) {
                Text(text = "button Text")
            }
            Button(onClick = { selectedTabIndex = 1 }) {
                Text(text = "button Text")
            }
            Button(onClick = { selectedTabIndex = 2 }) {
                Text(text = "button Text")
            }
            Button(onClick = { selectedTabIndex = 3 }) {
                Text(text = "button Text")
            }
        }

//        val dataList = listOf("Abhishek", "Harshit", "Gaurav", "Avin", "Avish", "Tanu","Manu","Swarnim","Surbhi","Swati","Shruti","Pranav",)
//
//        LazyColumn {
//            items(dataList) { item ->
//                Text(text = item)
//            }
//        }
    }
}


@SuppressLint("ModifierFactoryExtensionFunction")
private fun getButtonModifier(): Modifier {
    return Modifier.padding(13.dp)
}

private fun Modifier.setBtnModifier(): Modifier {
    return this
        .padding(3.dp)
        .clickable {}
        .border(3.dp, Color.Blue, CircleShape)
        .padding(25.dp)
}

/** 自定義Shape 三角形*/
class MyFirstShape : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val trianglePath = Path().apply {
            moveTo(size.width / 2f, -50f)
            lineTo(x = size.width, y = size.height)
            lineTo(x = 0f, y = size.height)
        }
        return Outline.Generic(path = trianglePath)
    }
}

@Parcelize
data class TestData(
    val title: String,
    val count: Int
) : Parcelable, Serializable

/**
 * 自定義NavType
 * Compose傳遞Data Class */
class TestDataParamType : JsonNavType<TestData>(clazz = TestData::class.java)