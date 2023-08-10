package com.example.common.Base.RxModel

import io.reactivex.subscribers.ResourceSubscriber

//you need implementation 'io.reactivex.rxjava2:rxjava:2.2.9'
/** 統一處理 成功或失敗*/
abstract class RxSubscriber<T> : ResourceSubscriber<T>() {

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        // 在這裡處理錯誤
        onError(404,"$e")
    }

    override fun onComplete() {}

    abstract fun onSuccess(t: T)

    abstract fun onError(code: Int, msg: String)
}