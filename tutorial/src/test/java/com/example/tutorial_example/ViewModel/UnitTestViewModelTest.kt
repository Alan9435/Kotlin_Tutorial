package com.example.tutorial_example.ViewModel

import android.util.Log
import com.example.common.Base.DataClass.QuoteList
import com.example.common.Base.DataClass.Result
import com.example.tutorial_example.utils.UnitTestUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UnitTestViewModelTest {
    private val quoteListData = mockk<QuoteList>()
    private val until = mockk<UnitTestUtils>()
    private val mockFoo = mockk<Foo>()

    @Before
    fun initMocks() {
        // every { Y } returns Z 當你取 Y 的時候 他會返回 Z (正確值)
        //相當於 api 返回給我 quoteListData.count = 5 的資料
        every { quoteListData.count } returns 5

        // 當我執行until.isCountEnough(quoteListData) 時他應該返回true 因為 5 > 0
        every { until.isCountEnough(quoteListData) } returns true

        // 設定quoteListData.results 第一筆 length = 50
        every { quoteListData.results[0].length } returns 50

        // 傳入quoteListData.results[0] 驗證length 因 >= 50 預期結果為true
        every { until.isLongEnough(quoteListData.results[0]) } returns true

        // 設定 bar 這個實作類別 回傳為TEST
        every { mockFoo.bar() } returns "TEST"
    }

    @Test
    fun testGetQuote() {
        Assert.assertEquals("TEST", mockFoo.bar())
        Assert.assertTrue(until.isCountEnough(quoteListData))
        Assert.assertTrue(until.isLongEnough(quoteListData.results[0]))
    }
}

interface Foo {
    fun bar(): String
}