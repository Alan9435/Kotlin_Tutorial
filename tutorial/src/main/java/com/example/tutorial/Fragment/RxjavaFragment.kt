package com.example.tutorial.Fragment

import android.util.Log
import com.example.common.Base.BaseFragment
import com.example.tutorial.databinding.FragmentRxjavaBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/** 通常綁定API呼叫使用
 *  EX:  apiService.getUser(1).sub.....
 *  API rs : Observable<Response<User>>....*/

/**
 * observeOn() 調度器
 * Schedulers.io() 執行非同步操作 適合呼叫API 查DataBase等長時間操作
 * Schedulers.newThread() 創建新執行續 適合短時間操作 ex:數據計算
 * Schedulers.computation() 計算執行續 適合耗時的計算操作 ex:圖像處理、數學計算
 * AndroidSchedulers.mainThread() 主執行續 android只能在主執行續更新UI */
class RxjavaFragment: BaseFragment<FragmentRxjavaBinding>() {
    private val compositeDisposable = CompositeDisposable() //存放disposable的容器 利於註銷

    override fun onViewInit() {

        binding.btnTest.setOnClickListener {
            testFilter()
            testMap()
            fetchData()
        }
    }

    private fun testFilter(){
        val disposable = Observable.range(1,10)
            .subscribeOn(Schedulers.io())   //切換至Io線程 subscribeOn切換執行緒並不會立即觸發執行緒切換，而是在事件發生時才進行切換
            .filter { it % 2 == 1 } //過濾條件 與kotlin filter用法一樣
            .observeOn(Schedulers.newThread())
            .delay(500,TimeUnit.MICROSECONDS)
            .subscribe{ result ->
                Log.d("****", "testFilter: $result")
            }
        compositeDisposable.add(disposable)
    }

    private fun testMap(){
        // 訂閱 Observable
        val disposable = Observable.just("hi","word")
            .subscribeOn(Schedulers.io()) // 在 IO 執行緒中執行
            .observeOn(Schedulers.newThread()) // 在新的執行緒中觀察
            .map { it + "test" } // 對資料進行操作 這裡把字串都加入test結尾
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                Log.d("*****", "testMap: $result")
            }
        compositeDisposable.add(disposable)
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