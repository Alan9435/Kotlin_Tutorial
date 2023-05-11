package com.example.tutorial.Fragment

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.common.Base.BaseFragment
import com.example.common.Base.Extensins.isBoolean
import com.example.tutorial.Fragment.Service.MusicService
import com.example.tutorial.R
import com.example.tutorial.databinding.FragmentServiceMusicBinding

class ServiceMusicFragment: BaseFragment<FragmentServiceMusicBinding>() {

    private lateinit var serviceIntent: Intent
    private var musicService: MusicService? = null
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("********", "onServiceConnected: in")
            val binder = service as MusicService.MusicBinder        //建立服務時被調用
            musicService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {  //用於當連接的Service意外斷開時調用 在這裡釋放資源
            Log.d("********", "onServiceDisconnected: in")
            musicService = null                 //設為null GC會自己回收
        }
    }
    override fun onViewInit() {
        serviceIntent = Intent(activity,MusicService :: class.java)
        activity?.startService(serviceIntent)    //啟動Service
        activity?.bindService(serviceIntent,serviceConnection,Context.BIND_AUTO_CREATE) //綁定Service

        binding.btnPlay.setOnClickListener {
            if(musicService?.isPlay().isBoolean()){
                binding.btnPlay.text = "Start"
                musicService?.musicPause()
            }else{
                binding.btnPlay.text = "Stop"
                musicService?.musicStart()
            }
        }

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        musicService?.apply {
            releaseMP()    //釋放MediaPlayer
            stopService()   //關閉Service
        }
        Log.d("********", ":des in")
//        musicService?.musicStop()            //釋放資源
        activity?.unbindService(serviceConnection)      //解除綁定
    }

    /**
     * onPause時 暫停音樂 */
//    override fun onStart() {
//        super.onStart()
//        if(!musicService?.isPlay().isBoolean() && musicService != null){
//            musicService?.musicStart()
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if(musicService?.isPlay().isBoolean() && musicService != null){
//            musicService?.musicPause()
//        }
//    }
}

