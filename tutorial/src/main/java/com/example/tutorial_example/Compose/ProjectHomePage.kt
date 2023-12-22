package com.example.tutorial_example.Compose

import android.content.Context
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.tutorial_example.Compose.common.route.RouteManager.HomeContainer
import com.example.tutorial_example.Compose.common.route.RouteManager.HomePage

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
            Log.d("***** ", "homeGraph: ${it.arguments?.serializable<LoginUiState.LoginResponseData>(HomeContainer.value)}")
            HomePage()
        }
    }
}

//由toolbar homeContent bottomBar組成
@Preview
@Composable
fun HomePage() {
    Log.d("*********", "HomePage: render")
    Text(text = "home")
}