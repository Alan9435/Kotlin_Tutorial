package com.example.tutorial_example.Compose

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import java.io.Serializable

/** 自定義NavType*/
abstract class JsonNavType<T: Parcelable>(private val clazz: Class<T>) : NavType<T>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): T? = bundle.serializable(key)

    override fun parseValue(value: String): T = Gson().fromJson(value, clazz)

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putParcelable(key, value)
    }
}

fun enCodeUri(dataClass : Parcelable): String? {
    return Uri.encode(Gson().toJson(dataClass))
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    //如果版本api版本>= 33 的話使用getSerializable取得序列化物件 並指定類型為 T::class.java
    //否則getSerializable已經棄用
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}