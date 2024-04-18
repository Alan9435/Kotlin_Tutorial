package com.example.tutorial_example.utils

import com.example.common.Base.DataClass.QuoteList
import com.example.common.Base.DataClass.Result


class UnitTestUtils {
    fun isCountEnough(quoteList: QuoteList): Boolean {
        return quoteList.count >= 0
    }

    fun isLongEnough(result: Result): Boolean {
        return result.length >= 50
    }

    fun isLoginAccountVerify(loginAccount: String): Boolean {
        if (loginAccount.length >= 6) {
            return loginAccount.uppercase().first() in 'A'..'Z'
        }

        return false
    }
}