package com.example.tutorial_example.Compose.common

import ir.kaaveh.sdpcompose.sdp

const val appbarHeight = 56

sealed class UserState {
    object SignedOut : UserState()
    object SignIn : UserState()
}