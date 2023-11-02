package com.example.common.Base.Extensins

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

fun Boolean?.isBoolean(): Boolean {
    return this == true
}

fun Int?.toSafeString(): String {
    return this?.toString() ?: ""
}

fun Int?.isNotNull(): Boolean {
    return this != null
}

fun Context?.hasPermission(permissionType: String): Boolean {    //確認是否開啟權限
    return this?.let { ContextCompat.checkSelfPermission(it, permissionType) } ==
            PackageManager.PERMISSION_GRANTED
}

fun Context?.hasRequiredRuntimePermissions(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { //如果API >= 31
        hasPermission(Manifest.permission.BLUETOOTH_SCAN) &&
                hasPermission(Manifest.permission.BLUETOOTH_CONNECT)
    } else {
        hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}