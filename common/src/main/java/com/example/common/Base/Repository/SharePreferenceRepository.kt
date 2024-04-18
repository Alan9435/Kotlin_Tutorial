package com.example.common.Base.Repository

import android.content.Context

class SharePreferenceRepository(val context: Context) {
    companion object {
        const val SharePrefName = "USER_DATA"
    }

    fun saveUserId(id: String) {
        val sharedPreferences =
            context.getSharedPreferences(SharePrefName, Context.MODE_PRIVATE) //只有此app能讀取
        sharedPreferences.edit().putString("User_id", id).apply()
    }
}

class SharePreferenceRepositoryPackage(private val prefManager: SharePrefManager) {

    fun saveUserIds(id: String) {
        prefManager.saveUserId(id)
    }
}