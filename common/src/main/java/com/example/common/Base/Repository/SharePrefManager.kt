package com.example.common.Base.Repository

import android.content.Context
import android.content.SharedPreferences
import com.example.common.Base.Repository.SharePreferenceRepository.Companion.SharePrefName

class SharePrefManager(context: Context): ISharedPreferenceManager {
    private var sharePref: SharedPreferences = context.getSharedPreferences(SharePrefName, Context.MODE_PRIVATE)

    override fun saveString(key: String, str: String) {
        sharePref.edit().putString(key, str).apply()
    }

    override fun getString(key: String): String? {
        return sharePref.getString(key,"")
    }
}

interface ISharedPreferenceManager {
    fun saveString(key: String, str: String) {}

    fun getString(key: String): String?
}