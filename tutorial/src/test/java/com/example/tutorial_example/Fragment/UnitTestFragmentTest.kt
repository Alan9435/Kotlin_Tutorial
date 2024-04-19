package com.example.tutorial_example.Fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.common.Base.Repository.ISharedPreferenceManager
import com.example.common.Base.Repository.SharePrefManager
import com.example.common.Base.Repository.SharePreferenceRepository
import com.example.common.Base.Repository.SharePreferenceRepository.Companion.SharePrefName
import com.example.common.Base.Repository.SharePreferenceRepositoryPackage
import com.example.tutorial_example.utils.UnitTestUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


// 參考文章： https://medium.com/@evanchen76/android-tdd-%E7%B3%BB%E5%88%97-10-mock-android-framework-55831ceb2054
@RunWith(JUnit4::class) // 使用 JUnit 4 框架 運行測試
class UnitTestFragmentTest {

    private lateinit var unitTestFragment: UnitTestFragment

    @Before //在測試前 會先執行的程式碼 通常用於設置測試環境或數據
    fun setUp() {
        unitTestFragment = UnitTestFragment()
    }

    @Test // 表明 testAdd 是一個測試方法 所以拿掉後run箭頭會消失
    fun testAdd() {
        val result = unitTestFragment.add(2, 3)
        assertEquals(5, result)
    }

    @Ignore //忽略測試 所以即便錯誤也會通過
    @Test
    fun testReverseString() {
        val input = "how are you"
        val output = "uoy era woh" //預期輸出

        val result = unitTestFragment.reverseString(input = input)

        assertEquals(output, result)
    }

    // 驗證帳號輸入是否正確
    @Test
    fun isLoginAccountVerify() {
        val util = UnitTestUtils()
        assertTrue(util.isLoginAccountVerify("A1234566"))
        assertFalse(util.isLoginAccountVerify("a122"))
        assertFalse(util.isLoginAccountVerify("A122"))
        assertFalse(util.isLoginAccountVerify("12223123"))
    }

    // 逐步測試
    @Test
    fun saveUserId() {
        // mock context, SharedPreferences, SharedPreferences.Editor
        // 因為中間步驟會回傳 SharedPreferences.Editor 方便驗證 有執行.edit(), .putString()... 等步驟
        val context = mockk<android.content.Context>()
        val sharePrefs = mockk<SharedPreferences>()
        val sharePrefsEditor = mockk<SharedPreferences.Editor>()

        // 測試參數
        val userAccount = "A1234567"
        // 欄位key
        val shKey = "User_id"

        // 為了正確的呼叫 sharePreferenceRepository.saveUserId(userAccount)
        // 需要將其function內執行的步驟皆模擬出來 否則測試錯誤
        // 呼叫 { ... } 時返回 sharePrefs 物件
        every { context.getSharedPreferences(SharePrefName, MODE_PRIVATE) } returns sharePrefs
        // 呼叫 { ... } 時返回SharedPreferences.Editor 物件
        every { sharePrefs.edit() } returns sharePrefsEditor
        every { sharePrefsEditor.putString(shKey, userAccount) } returns sharePrefsEditor
        // 呼叫 { ... } 時返回 Unit物件
        every { sharePrefsEditor.apply() } returns Unit

        // 執行被測試的物件, 方法
        val sharePreferenceRepository = SharePreferenceRepository(context = context)
        sharePreferenceRepository.saveUserId(userAccount)

        //verify(exactly = 1) { X } 驗證X方法 是否有被執行一次
        verify(exactly = 1) { context.getSharedPreferences(SharePrefName, MODE_PRIVATE) }
        verify(exactly = 1) { sharePrefs.edit() }
        verify(exactly = 1) { sharePrefsEditor.putString(shKey, userAccount) }
        verify(exactly = 1) { sharePrefsEditor.apply() }
    }

    // 僅測試是否正確的將參數傳入 function
    @Test
    fun saveIds() {
        // 建立 mock 物件
        val sharePreferenceManager = mockk<SharePrefManager>()

        every { sharePreferenceManager.saveString(any(), any()) } returns Unit

        val userId = "A1234567"
        val sharePreferenceRepository = SharePreferenceRepositoryPackage(sharePreferenceManager)

        sharePreferenceRepository.saveUserIds(userId)

        verify(exactly = 1) { sharePreferenceRepository.saveUserIds(userId) } // 驗證 sharePreferenceManager.saveUserId 是否被呼叫一次，且傳入正確的參數
    }

    @After //在每個測試案例後 運行該方法 通常用於清理測試環境
    fun clean() {

    }
}