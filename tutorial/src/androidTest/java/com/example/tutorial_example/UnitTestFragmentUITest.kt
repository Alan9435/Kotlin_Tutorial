package com.example.tutorial_example

import android.content.Context
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.tutorial_example.Fragment.UnitTestFragment
import com.example.tutorial_example.Fragment.UnitTestPage2Fragment
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Navigation Test 參考資料: https://developer.android.com/guide/navigation/testing#groovy
 * */
// 當你會用到網路存取資料、資料庫、多執行緒等等較花費時間，在你的測試類別上方加上@LargeTest
// 測試執行時間會超過1秒的，一般都會被歸為LargeTest。
@LargeTest
@RunWith(AndroidJUnit4::class)
class UnitTestFragmentUITest {
    private lateinit var mContext: Context
    private lateinit var unitTestFragmentScenario: FragmentScenario<UnitTestFragment>
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        //啟動UnitTestFragment 否則onView...查找不到對應元件id
        unitTestFragmentScenario = launchFragmentInContainer<UnitTestFragment>()

        // 初始化一個TestNavHostController
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        mContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    // 測試註冊成功
    @Test
    fun registrationSuccess() {
        // 對etAccount 元件輸入A123456789 並關閉鍵盤,
        // 處於gone或invisible時無法輸入 所以會導致錯誤 (控件必須可見)
        onView(withId(R.id.etAccount)).perform(
            typeText("A123456789"),
            ViewActions.closeSoftKeyboard()
        )
        //對etPwd 元件輸入F123A123 並關閉鍵盤
        onView(withId(R.id.etPwd)).perform(typeText("F123A123"), ViewActions.closeSoftKeyboard())

        // 測試畫面是否有吻合A123456789的輸入
        onView(withText("A123456789")).check(matches(isDisplayed()))
        // 測試畫面是否有吻合F123A123的輸入
        onView(withText("F123A123")).check(matches(isDisplayed()))

        unitTestFragmentScenario.onFragment { fragment ->
            // 設定Graph在 TestNavHostController
            navController.setGraph(R.navigation.navigation_unit_test)

            // 透過findNavController介面 使navController可用 並將navController指派給fragment
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // 點擊btnRegister元件
        onView(withId(R.id.btnRegister)).perform(click())

        //驗證執行點擊是否會變更 NavController 的狀態
        Assert.assertEquals(navController.currentDestination?.id, R.id.unitTestPage2Fragment)

        // 啟動UnitTestPage2Fragment 因為切換fragment了
        val unitTestPage2FragmentScenario= launchFragmentInContainer<UnitTestPage2Fragment>()

        //是否顯示註冊成功
        onView(withText(mContext.getString(R.string.unittest_register_success))).check(matches(isDisplayed()))
    }

    //測試註冊失敗
    @Test
    fun registrationFail() {
        // 對etAccount 元件輸入A123456789 並關閉鍵盤,
        // 處於gone或invisible時無法輸入 所以會導致錯誤 (控件必須可見)
        onView(withId(R.id.etAccount)).perform(
            typeText("A123"),
            ViewActions.closeSoftKeyboard()
        )
        //對etPwd 元件輸入F123A123 並關閉鍵盤
        onView(withId(R.id.etPwd)).perform(typeText("TestTest"), ViewActions.closeSoftKeyboard())

        // 點擊btnRegister元件
        onView(withId(R.id.btnRegister)).perform(click())

        // 搜尋限制為root中的Dialog 是否有顯示"註冊失敗"
        onView(withText(mContext.getString(R.string.unittest_register_fail)))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    @After
    fun clean() {

    }
}