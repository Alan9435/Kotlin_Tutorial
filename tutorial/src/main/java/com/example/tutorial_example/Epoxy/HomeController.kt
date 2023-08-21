package com.example.tutorial_example.Epoxy

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.carousel
import com.example.common.Base.InterfaceCallback


class HomeController(
    private val callback: InterfaceCallback?
): EpoxyController() {
    var headItemData: List<HeadData> = emptyList() //定義data 並初始化為空陣列
        set(value) {            //定義setter方法 當data被設置時會執行方法
            field = value          //filed表示該屬性本身 所以field = value 等同把data的值設為value
            /**
             * 觸發重新構建模型（model）的過程
             * 以更新 RecyclerView 的顯示*/
            requestModelBuild()
        }

    var messageItemData: List<MessageData> = emptyList()
        set(value){
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        //資料塞入 當資料變動時會隨之改變
        val model = headItemData.map { item ->
            HeadItemModel_().apply {
                setCallBack(this@HomeController.callback)
                id(item.iconText)
                icon(item.icon)
                tvIcon(item.iconText) //設定在HeadItemModel 中定義的變數
            }
        }

        carousel{
            id("HeadList")  //區分不同的carousel 布局更新 , 項目綁定和點擊事件
            models(model) //設定modal
            //整條rv的內距 itemSpacingDp各項的間距
            padding(Carousel.Padding.dp(13,13,13,13,20))
        }

        messageItemData.forEachIndexed{ index, item ->
            MessageItemModel_()
                .id(index)
                .tvSubTitle(item.subTitle)
                .tvContent(item.content)
                .addTo(this)
        }
    }
}
