package com.example.tutorial_example.Compose

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tutorial_example.Compose.common.model.ProductDetailData
import com.example.tutorial_example.Compose.common.route.RouteManager


fun NavGraphBuilder.productDetailGraph(navController: NavController, context: Context) {

    composable(
        route = RouteManager.ProductDetailPage.getRoute(),
        arguments = listOf(
            navArgument(RouteManager.ProductDetailPage.value) {
                type = ProductDetailDataParamType()
            }
        )
    ) { backStackEntry ->
        val data = backStackEntry.arguments?.serializable<ProductDetailData>(RouteManager.ProductDetailPage.value)
        data?.let {
            ProductDetailPage(navController = navController, productDetailData = data, context = context)
        }
    }
}

@Composable
fun ProductDetailPage(navController: NavController, productDetailData: ProductDetailData = ProductDetailData(), context: Context) {
    Log.d("*********", "ProductDetailPage: ${productDetailData}")
    Column {
        ProductInfoPage(context = context, infoData = productDetailData)
    }
}

class ProductDetailDataParamType : JsonNavType<ProductDetailData>(
    clazz = ProductDetailData::class.java
)