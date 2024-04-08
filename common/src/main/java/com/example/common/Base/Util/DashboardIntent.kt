package com.example.common.Base.Util

/**
 * 管理使用者的 intent 行為
 * */
sealed class DashboardIntent {
    data object GetQuote: DashboardIntent()

    // other case
}