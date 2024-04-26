package com.example.tutorial_example

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.tutorial_example.Fragment.UnitTestFragment
import org.junit.Test
import org.junit.runner.RunWith

// 當你會用到網路存取資料、資料庫、多執行緒等等較花費時間，在你的測試類別上方加上@LargeTest
// 測試執行時間會超過1秒的，一般都會被歸為LargeTest。
@LargeTest
@RunWith(AndroidJUnit4::class)
class UnitTestFragmentUITest {
    //啟動UnitTestFragment 否則onView...查找不到對應元件id
    val fragmentScenario = launchFragmentInContainer<UnitTestFragment>()

    @Test
    fun uiTest() {
        // 對etAccount 元件輸入A123456789 並關閉鍵盤,
        // 處於gone或invisible時無法輸入 所以會導致錯誤 (控件必須可見)
        onView(withId(R.id.etAccount)).perform(
            typeText("A123456789"),
            ViewActions.closeSoftKeyboard()
        )
        //對etPwd 元件輸入F123A123 並關閉鍵盤
        onView(withId(R.id.etPwd)).perform(typeText("F123A123"), ViewActions.closeSoftKeyboard())
        // 點擊btnRegister元件
        onView(withId(R.id.btnRegister)).perform(click())

        // 測試畫面是否有吻合A123456789的輸入
        onView(withText("A123456789")).check(matches(isDisplayed()))
        // 測試畫面是否有吻合F123A123的輸入
        onView(withText("F123A123")).check(matches(isDisplayed()))
    }
}