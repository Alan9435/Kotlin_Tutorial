package com.example.tutorial_example.Compose.common.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.tutorial_example.Compose.common.modifier.setAlertDialogButton
import com.example.tutorial_example.Compose.common.modifier.setBoxToCutCornerShape
import com.example.tutorial_example.Compose.common.modifier.setLoadingIcon
import com.example.tutorial_example.Compose.common.modifier.setLoadingMask
import com.example.tutorial_example.Compose.ui.theme.Blue20
import com.example.tutorial_example.Compose.ui.theme.Purple20
import com.example.tutorial_example.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


//todo 為個別元件開file

/** 基本的共用按鈕 */
@Preview
@Composable
fun ButtonCompose(
    modifier: Modifier = Modifier,
    buttonText: String = "測試",
    buttonTextModifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(onClick = { onClick.invoke() }, modifier = modifier) {
        Text(text = buttonText, modifier = buttonTextModifier)
    }
}

/** 基本的共用EditText */
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EditTextCompose(
    modifier: Modifier = Modifier,
    defaultValue: String = "",
    maxLines: Int = 1,
    singleLine: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(10),
    label: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    showPwd: Boolean = true,
    placeholder: @Composable () -> Unit = { Text(text = "測試 placeholder") },
    onValueChange: (String) -> Unit = {},
    inputFilter: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    OutlinedTextField(
        value = defaultValue,
        modifier = modifier,
        maxLines = maxLines,
        singleLine = singleLine,
        shape = shape,
        placeholder = { placeholder.invoke() },
        trailingIcon = { trailingIcon.invoke() },
        onValueChange = {
            onValueChange.invoke(it)
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = if (showPwd) {
            VisualTransformation.None   //預設
        } else {
            PasswordVisualTransformation() //隱碼
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Blue20,
            unfocusedBorderColor = Color.Transparent
        ),
        keyboardActions = keyboardActions
    )
}

@Preview
@Composable
fun SecretCheckBox(checked: Boolean = false, onCheckedChange: (Boolean) -> Unit = {}) {
    Image(
        painter = if (checked) {
            painterResource(R.drawable.baseline_disc_full_24)
        } else {
            painterResource(R.drawable.baseline_do_disturb_off_24)
        },
        contentDescription = if (checked) { //通常便於無障礙使用
            "your input is show now"
        } else {
            "your input is hide now"
        },
        modifier = Modifier
            .clickable { onCheckedChange(!checked) }
            .size(25.sdp)
    )
}

@Preview
@Composable
fun BaseAlertDialog(
    msg: String = "test test test",
    backgroundEnable: Boolean = false,
    onDismissRequest: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
    icon: ImageVector = Icons.Default.Info,
    ) {
    AlertDialog(
        onDismissRequest = onDismissRequest,  //背景是否禁用 為空時->不可點
        text = {
            Box(
                modifier = Modifier.setBoxToCutCornerShape()
            ) {
                Text(
                    text = msg,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        },
        modifier = Modifier,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = "Dialog Icon",
                modifier = Modifier.size(50.sdp)
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmClick,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Purple20)
            ) {
                Text(
                    text = stringResource(id = R.string.compose_button_confirm),
                    color = Color.White,
                    modifier = Modifier.setAlertDialogButton(),
                    textAlign = TextAlign.Center
                )
            }
        },
    )
}

/** 遮罩組件 */
@Preview
@Composable
fun LoadingMask() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset(assetName = "loading.json")
    )

    Column(
        modifier = Modifier.setLoadingMask(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.setLoadingIcon(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                modifier = Modifier
                    .size(160.sdp)
                    .scale(1.5f),
                composition = composition,
                iterations = LottieConstants.IterateForever //animate forever
            )

            Text(text = "Loading...",fontSize= 18.ssp)
        }
    }
}