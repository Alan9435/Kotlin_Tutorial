package com.example.tutorial_example.Compose

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.tutorial_example.Compose.common.route.RouteManager.StoreListPage

fun NavGraphBuilder.storeListGraph(navController: NavController, context: Context) {
    composable(route = StoreListPage.routeName) {
        StoreListPage(navController = navController)
    }
}

@Composable
fun StoreListPage(navController: NavController) {
    Button(onClick = { navController.popBackStack() }) {
        Text(text = "btn")
    }

    Text(text = "Store")
}