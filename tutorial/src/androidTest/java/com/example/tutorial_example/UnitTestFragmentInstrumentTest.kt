package com.example.tutorial_example

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.common.Base.Repository.ISharedPreferenceManager
import com.example.common.Base.Repository.SharePrefManager
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UnitTestFragmentInstrumentTest {

    /**
     * build.gradle (Module :tutorial -61~62行)
     * merges += "META-INF/LICENSE.md"
     * merges += "META-INF/LICENSE-notice.md"
     * 指示 Gradle 將 META-INF/LICENSE.md 和 META-INF/LICENSE-notice.md 檔案合併到 APK 中
     * 需要這2個檔案的授權的資訊 才可執行 Instrument test
     * */
    // Instrument Test 測試SharedPreference 是否正常存取
    @Test
    fun saveIds() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val key = "User_Id"
        val value = "A123456789"
        val sharedPreferenceManager = SharePrefManager(appContext)
        sharedPreferenceManager.saveString(key, value)
        val valueFromSP = sharedPreferenceManager.getString(key)
        //將SharedPreference取出，驗證結果
        Assert.assertEquals(value, valueFromSP)
    }
}