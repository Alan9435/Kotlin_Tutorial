package com.example.tutorial_example.Fragment

import android.util.Log
import com.example.common.Base.BaseFragment
import com.example.common.Base.Util.RxSchedulers
import com.example.common.Base.Util.RxSubscriber
import com.example.tutorial_example.databinding.FragmentRxjavaBinding
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
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
class RxjavaFragment : BaseFragment<FragmentRxjavaBinding>() {
    private val compositeDisposable = CompositeDisposable() //存放disposable的容器 利於註銷

    override fun onViewInit() {

        binding.btnTest.setOnClickListener {
            testFilter()
            testMap()
            fetchData()
            getInfo()
            testZip()
            testConcat()
        }
    }


    /** zip 等待2個API都呼叫完成 */
    private fun testZip() {
        var i = 0
        val obA = Observable.create<String> { e ->
            Thread.sleep(5000)

            //假設撥打3次才成功
            if (i <= 2) {
                e.onError(Throwable("a error"))
            } else {
                e.onNext("obA done")
            }
            i++
            e.onComplete()
        }.subscribeOn(Schedulers.io())

        val obB = Observable.create<String> { e ->
            Thread.sleep(3000)
            e.onNext("obB done")
            e.onComplete()
        }.subscribeOn(Schedulers.io())

        Observable.zip(
            obA,
            obB
        ) { a, b ->
            Log.d("*********", "test")
            "testZip: done ${a + b}"
        }
//            .retry(3)  //retry時不會進到onError
            .subscribe(getTheObserver())

    }

    private fun getTheObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onNext(t: String) {
                //2個API都成功的後續動作
                Log.d("*********", t)
            }

            override fun onSubscribe(d: Disposable) {
                Log.d("*********", "onSubscribe")
            }

            override fun onError(e: Throwable) {
                Log.d("*********", "onError : ${e.message}")
            }

            override fun onComplete() {
                Log.d("*********", "onComplete")
            }
        }
    }


    private fun testFilter() {
        val disposable = Observable.range(1, 10)
            .subscribeOn(Schedulers.io())   //切換至Io線程 subscribeOn切換執行緒並不會立即觸發執行緒切換，而是在事件發生時才進行切換
            .filter { it % 2 == 1 } //過濾條件 與kotlin filter用法一樣
            .observeOn(Schedulers.newThread())
            .delay(500, TimeUnit.MICROSECONDS)
            .subscribe { result ->
                Log.d("****", "testFilter: $result")
            }
        compositeDisposable.add(disposable)
    }

    private fun testMap() {
        // 訂閱 Observable
        val disposable = Observable.just("hi", "word")
            .subscribeOn(Schedulers.io()) // 在 IO 執行緒中執行
            .observeOn(Schedulers.newThread()) // 在新的執行緒中觀察
            .map { it + "test" } // 對資料進行操作 這裡把字串都加入test結尾
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                Log.d("*****", "testMap: $result")
            }
        compositeDisposable.add(disposable)
    }

    private fun testConcat() {
        val observableA = Observable.fromArray("A1", "A2", "A3", "A4").delay(3,TimeUnit.SECONDS)
        val observableB = Observable.fromArray("B1", "B2", "B3", "B4")

        Observable.concat(observableA, observableB)
            .subscribe(getObserver())
    }

    private fun getObserver(): Observer<String> {
        return object : Observer<String> {

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(value: String) {
                Log.d("*******", "testConcat onNext: ${value}")
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        }
    }


    private fun fetchData() {
        val disposable = Observable.just("Hello Reactive World")//模擬API回傳
            .subscribeOn(Schedulers.io())   //切換至Io線程
            .observeOn(AndroidSchedulers.mainThread()) //切換至主線程
            .subscribe(     //接收數據回傳 3個function callback
                { v ->
                    binding.tvTest.text = v
                }, //Next
                { errorMsg ->
                    binding.tvTest.text = errorMsg.toString()
                }, //錯誤
                { Log.d("***********", ": finish") } //完成
            )
        compositeDisposable.add(disposable)  //disposable添加至CompositeDisposable 利於取消訂閱
    }

    /** 模擬api回傳 這段正常寫在viewModel 或 presenter*/
    private fun getInfo() {
        val data: Flowable<*> = Flowable.just("my test title").map { TestData(title = it) }
        data.onBackpressureBuffer() //背壓 平衡二者之間壓力的機制，避免Subscriber被淹沒(內存溢出 系統崩潰)
            //用於將一個 Observable 或 Flowable 與一個或多個 ObservableTransformer 或
            // FlowableTransformer 組合在一起，以形成一個新的 Observable 或 Flowable。
            //.subscribeOn(Schedulers.io()) //指定ob操作在IO線程執行
            //.observeOn(AndroidSchedulers.mainThread()) //指定訂閱者的操作在主線程執行
            .compose(RxSchedulers.applyFlowableSchedulers())
//            .doOnSubscribe{}//訂閱開始時執行的操作
//            .doFinally{}//資料串流結束後動作
            //.onBackpressureBuffer //
            //.doOnNext { KLog.i("測試") }  //每次發射資料後 執行
            //.doOnError { KLog.i("測試錯誤") }   //錯誤時執行
            //.doOnComplete { KLog.i("測試完成")  }  //完成時執行 不包含錯誤時
            //getNewStoreInfo(storeId, groupId)的結果
            .subscribeWith(object : RxSubscriber<Any>() {
                override fun _onNext(t: Any) {
                    if (t is TestData) {
                        Log.d("******", "_onNext: 成功動作 ${t.title}")
                    }
                }

                override fun _onError(code: Int, msg: String) {
                    Log.d("******", "_onError: 失敗動作")
                }
            }).add(compositeDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()  //取消訂閱 避免OOM
    }

    data class TestData(
        val title: String = ""
    )
}
