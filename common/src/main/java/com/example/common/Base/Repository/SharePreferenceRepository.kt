package com.example.common.Base.Repository

import android.content.Context
import com.example.common.Base.Repository.SharePreferenceRepository.Companion.SH_USER_ID

class SharePreferenceRepository(val context: Context) {
    companion object {
        const val SharePrefName = "USER_DATA"
        const val SH_USER_ID = "User_id"
    }

    fun saveUserId(id: String) {
        val sharedPreferences =
            context.getSharedPreferences(SharePrefName, Context.MODE_PRIVATE) //只有此app能讀取
        sharedPreferences.edit().putString("User_id", id).apply()
    }
}

class SharePreferenceRepositoryPackage(private val prefManager: SharePrefManager) {

    fun saveUserIds(id: String) {
        prefManager.saveString(SH_USER_ID ,id)
    }
}