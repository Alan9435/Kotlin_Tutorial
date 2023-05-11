package com.example.common.Base.Extensins

fun Boolean?.isBoolean() : Boolean{
    return this == true
}

fun Int?.toSafeString() : String{
    return this?.toString() ?: ""
}