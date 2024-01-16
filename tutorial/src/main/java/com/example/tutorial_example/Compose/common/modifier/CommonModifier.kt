package com.example.tutorial_example.Compose.common.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.tutorial_example.Compose.common.appbarHeight
import com.example.tutorial_example.Compose.ui.theme.TransparentGray
import ir.kaaveh.sdpcompose.sdp

/**
 * 放置Modifier Extension用
 * 可重複延展 Ex: Modifier.setButton().setLoginPageEditText()*/

//composed{} 用於定義可組合的組件
//為了保持Modifier的可組合性和避免對原始Modifier進行不可變更的修改
//所以使用composed{} 創建新的Modifier
fun Modifier.setBaseButtonModifier(): Modifier = composed {
    this.padding(10.sdp,5.sdp)
}

fun Modifier.setLoginButton(): Modifier =
    this.setBaseButtonModifier()

fun Modifier.setLoginPageEditText(): Modifier = composed {
    this.padding(10.sdp).fillMaxWidth().height(50.sdp)
}

fun Modifier.setBoxToCutCornerShape(): Modifier = composed {
    this.fillMaxWidth()
        .wrapContentHeight()
        .clip(CutCornerShape(10.sdp))
        .background(Color.White)
        .padding(10.sdp)
}

fun Modifier.setAlertDialogButton(): Modifier = composed {
    this.fillMaxWidth()
        .wrapContentHeight()
        .padding(5.sdp)
}

//整層LoadingMask
fun Modifier.setLoadingMask(): Modifier = composed {
    this.background(TransparentGray)
        .fillMaxWidth()
        .fillMaxHeight()
        .clickable(
            onClick = {},
            indication = null, // 取消點擊波紋效果
            //interactionSource-> 追蹤交互事件 ex:點擊,拖曳
            interactionSource = remember {
                //用於追蹤事件的物件
                MutableInteractionSource()
            }
        )
}

//Loading區塊
fun Modifier.setLoadingIcon(): Modifier = composed {
    this.clip(CircleShape)
        .padding(10.sdp)
        .background(Color.White)
        .size(250.sdp)
}

//appbar
fun Modifier.setAppbar(): Modifier = composed {
    this.height(appbarHeight.sdp)
}