package com.example.common.Base.Util

import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

/** 統一處理 成功或失敗*/
abstract class RxSubscriber<T> : ResourceSubscriber<T>() {

    override fun onNext(t: T) {
        _onNext(t)
    }

    override fun onError(e: Throwable) {
        // 在這裡處理錯誤
    }

    override fun onComplete() {}

    abstract fun _onNext(t: T)

    abstract fun _onError(code: Int, msg: String)
}

