package com.example.common.Base.RxModel

import io.reactivex.FlowableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

//you need implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
class RxSchedulers {
    companion object{

        /** 調度器 執行api動作時會切換至io線程 更新ui時會切回主執行續*/
        fun <T> applyFlowableSchedulers(): FlowableTransformer<T, T> {
            return FlowableTransformer { observable ->
                observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }

        fun <T> applySingleTransformerSchedulers(): SingleTransformer<T, T> {
            return SingleTransformer { observable ->
                observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}