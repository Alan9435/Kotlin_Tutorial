package com.example.common.Base.Repository

import android.content.Context
import com.example.common.Base.Repository.SharePreferenceRepository.Companion.SharePrefName

class SharePrefManager(private val context: Context) {

    fun saveUserId(userId: String) {
        val sharePref = context.getSharedPreferences(SharePrefName, Context.MODE_PRIVATE)
        sharePref.edit().putString("User_id", userId).apply()
    }
}