package com.example.tutorial.Fragment

import android.annotation.SuppressLint
import android.util.Log
import com.example.common.Base.BaseFragment
import com.example.tutorial.databinding.FragmentRxjavaBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay

class RxjavaFragment: BaseFragment<FragmentRxjavaBinding>() {
    private val compositeDisposable = CompositeDisposable() //存放disposable的容器

    override fun onViewInit() {

        binding.btnTest.setOnClickListener {
            fetchData()
        }
    }

    private fun fetchData() {
        val disposable = Observable.just("Hello Reactive World" )//模擬API回傳
            .subscribeOn(Schedulers.io())   //切換至Io線程
            .observeOn(AndroidSchedulers.mainThread()) //切換至主線程
            .subscribe(     //接收數據回傳 3個function callback
                { v ->
                    binding.tvTest.text = v
                }, //Next
                { errorMsg ->
                    binding.tvTest.text = errorMsg.toString()
                }, //錯誤
                { Log.d("***********", ": finish")  } //完成
            )
        compositeDisposable.add(disposable)  //disposable添加至CompositeDisposable 利於取消訂閱
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()  //取消訂閱 避免OOM
    }
}