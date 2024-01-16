package com.example.tutorial_example.Compose

import com.example.tutorial_example.Compose.common.base.BaseViewModel
import java.lang.Thread.State

/** 用來存放需記憶的資料 ex:登入資料 */
class ActivityViewModel: BaseViewModel() {
    private var memberData: MemberData? = null

    fun setMemberData(data: MemberData) {
        memberData = null
        memberData = data
    }

    fun getMemberData(): MemberData? = memberData

    fun clearMemberData() {
        memberData = null
    }
}

data class MemberData(
    val userName: String = ""
)