package com.example.tutorial_example.Compose.common.route

//todo 看能不能優化
/** 限制參數量=1 要傳遞多種需包成Data class */
enum class RouteManager(val routeName: String, val valueCanNull: String = "?", val value: String = "") {
    /** 登入類的Graph */
    LoginContainer(routeName = "LoginContainer"),
    /** 登入頁 */
    LoginPage(routeName = "LoginPage"),
    /** 首頁類的Graph */
    HomeContainer(routeName = "HomeContainer", valueCanNull = "/" , value = "passValue"),
    /** 首頁 */
    HomePage(routeName = "HomePage");

    fun getRoute(): String {
        //HomeContainer/{passValue}
        return "$routeName$valueCanNull{$value}"
    }

    fun setSender(data: Any): String {
        //HomeContainer/data
        return "$routeName$valueCanNull$data"
    }
}