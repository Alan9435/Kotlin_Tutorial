package com.example.tutorial_example.Fragment

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


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

    @After //在每個測試案例後 運行該方法 通常用於清理測試環境
    fun clean() {

    }
}