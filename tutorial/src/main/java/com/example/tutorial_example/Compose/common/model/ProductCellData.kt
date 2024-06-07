package com.example.tutorial_example.Compose.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


data class HomeProductList(
    val productList: List<ProductCellData> = listOf()
)

/**
 * @param icon 圖片網址
 * @param detailData 詳細產品資料
 * */
data class ProductCellData(
    val icon: String = "",
    val detailData: ProductDetailData? = null
)

@Parcelize
data class ProductDetailData(
    val productName: String = "",
    val productNumber: Int = 0,
    val productPrice: String = ""
): Parcelable, Serializable
