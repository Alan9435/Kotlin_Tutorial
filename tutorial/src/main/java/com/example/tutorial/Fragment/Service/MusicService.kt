package com.example.tutorial.Fragment.Service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.common.Base.Extensins.isBoolean

class MusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        //參數二 替換成自己的音檔
        mediaPlayer = MediaPlayer.create(this, 0) //初始化mediaPlayer
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder { //透過override 方法 回傳MusicBinder class
        return MusicBinder()
    }

    fun stopService() {
        stopSelf()
    }

    fun isPlay() : Boolean = mediaPlayer?.isPlaying.isBoolean()

    fun musicStart(){
        mediaPlayer?.start()
    }

    fun musicPause(){
        mediaPlayer?.pause()
    }

    fun releaseMP(){
        mediaPlayer?.release()
        mediaPlayer = null
    }

    inner class MusicBinder : Binder() {  //定義一個內部類別 並返回實例
        fun getService(): MusicService = this@MusicService
    }
}
