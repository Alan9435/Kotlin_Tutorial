# 如何查看與運行程式碼
## 一. 各類第三方庫, 原生程式：
#### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1.切換分支至 `master`    
#### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2.前往`activty_main.xml`

```
  <!--  透過navGraph 調整需要進入的頁面  -->
<fragment
    android:id="@+id/mainFragment"
    android:name="androidx.navigation.fragment.NavHostFragment"
    android:layout_width="0dp"
    android:layout_height="0dp"
    app:defaultNavHost="true"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:navGraph="@navigation/navigation_stream" />
```
#### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3.修改 `app:navGraph="@navigation/navigation_stream"` 內的路徑導航至想運行的Navigation
#### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 4.Run app :clap: 
| Navigation |  描述  | 技術 | 
|   :---:    | :---: | :---: |
| navigation_connect_api  | 網路連線的基本範例 (未封裝)  | Retrofit、MVVM |
| navigation_epoxy  | 電商、GooglePlay UI頁面範例  | Epoxy |
| navigation_mvi | MVI架構範例  | MVI |
| navigation_recycleview  | Recyclerview與ListView效能評比以及實作範例  | RecyclerView、ListView、Profiler |
| navigation_rsa  | RSA加解密範例並使用Unit Test驗證(RsaFragmentTest)  | RSA、Unit Test |
| navigation_rxjava  | RxJava異步處理及操作符範例  | RxJava |
| navigation_service_music  | 背景播放音樂範例 | Service |
| navigation_stream  | 使用不同的串流協議實現影音播放  | ExoPlayer |
| navigation_unit_test  | 使用Mockk、Espresso實作單元測試、整合測試、UI測試 </br></br> 示例Class： UnitTestFragmentTest、UnitTestViewModelTest、UnitTestFragmentInstrumentTest、UnitTestFragmentUITest  | Unit Test、Instrumented Test、UI Test、Mockk、Espresso |

## 二. Compose UI：
### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;包含常見元件應用以及一個基本的登入流程
#### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1.切換分支至 `Compose`
#### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2.查看程式進入點 `ProjectTestActivity.kt`
#### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3.Run app :clap:

## 三. CircleCI：
### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;產生APK、AAB存放ARTIFACTS, 自動進版號, 發送信件、Slack通知
#### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1.切換分支至 `circleci-project-new-setup`
#### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2.前往路徑 `~/Kotlin_Tutorial/.circleci/config.yml`
#### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 3.參考它 :eyes:	
