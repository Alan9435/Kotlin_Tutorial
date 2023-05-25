package com.example.tutorial.Fragment

import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.os.Looper.prepare
import android.util.Log
import com.example.common.Base.BaseFragment
import com.example.tutorial.databinding.FragmentStreamBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.MimeTypes


class SteamFragment : BaseFragment<FragmentStreamBinding>() {
    private var player: ExoPlayer? = null
    /**串流一般都以分段進行 每個分段代表檔案或URL
     * 常見的串流協議：
     * HLS -> 多個分段的URL 撥放器根據清單中分段的URL進行請求和下載 可以根據網路狀況和設備能力調整影音的品質和傳輸速率 EX:Youtube
     * DASH -> 分段的URL或位置範圍
     * 每個分段通常包含URL、時間戳記
     * 傳輸過程中也要進行編碼與壓縮的動作
     * 常見的壓縮、編碼格式  AV1 H.264 H.265 VP9
     * AV1內建解碼僅支援Android 10以上且依設備有可能不支援 可見getAllCodeList()方法 */
    private val url = "https://www.ebookfrenzy.com/android_book/movie.mp4"
    override fun onViewInit() {
        initPlayer()
//        getAllCodeList()

    }

    private fun initPlayer() {

        context?.let {
            player = ExoPlayer.Builder(it).build()
        }

        binding.pvVideo.player = player

        player?.apply {
            playWhenReady = true    //準備好後自動播放與否
            setMediaSource(buildMediaSource())  //設定來源
            prepare()  //用於初始化播放器資源、設置媒體源、緩存媒體數
        }
    }

    private fun buildMediaSource(): MediaSource {
        //根據指定的媒體 URL 創建媒體項目（MediaItem），並將其傳遞給ProgressiveMediaSource
        //最後返回MediaSource物件供ExoPlayer使用
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(url))
    }

    /**以RTSP協議獲取H.264影片並播放*/
    private fun buildRtspMediaSource(): RtspMediaSource{
        val mediaItem = MediaItem.Builder().setMimeType(MimeTypes.VIDEO_H264) //編碼格式
            .setUri(url)        //使用前須把url替換成使用 RTSP 串流的 URL
            .build()

        return RtspMediaSource.Factory().createMediaSource(mediaItem)
    }

    /** 使用 HLS協議的串流*/
    private fun buildHlsMediaSource(): HlsMediaSource{
        val mediaItem = MediaItem.fromUri(url) // 使用前須把url替換成使用 HLS 串流的 URL
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        val hlsMediaSourceFactory = HlsMediaSource.Factory(dataSourceFactory)
        return hlsMediaSourceFactory.createMediaSource(mediaItem)
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }


    private fun releasePlayer() {
        //釋放資源
        player?.release()
        player = null
    }

    /** 設備上可用的媒體編碼器清單以及其名稱 */
    private fun getAllCodeList(){
        MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos.forEach {
            Log.d("*****", "getAllCodeLise: ${it.name}")
        }
    }
}