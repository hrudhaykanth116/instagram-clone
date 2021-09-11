package com.hrudhaykanth116.instagramclone.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import com.hrudhaykanth116.instagramclone.databinding.ViewMyYoutubePlayerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MyYoutubePlayer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mYouTubePlayer: YouTubePlayer? = null
    private var mCurrentVideoKey: String? = null

    private val binding = ViewMyYoutubePlayerBinding.inflate(
        LayoutInflater.from(
            context
        ), this, true
    )

    init {

        binding.youTubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                mYouTubePlayer = youTubePlayer
                mCurrentVideoKey?.let { youTubePlayer.cueVideo(it, 0f) }
            }
        })

    }

    fun videoPlayer(): YouTubePlayerView {
        return binding.youTubePlayer
    }

    fun setVideoKey(key: String){
        mCurrentVideoKey = key
        mYouTubePlayer?.cueVideo(key, 0f)
    }

    fun playYouTubeVideo(key: String, lifecycle: Lifecycle){
        // Loads only if the lifecycle is resumed, else cues it
        mYouTubePlayer?.loadOrCueVideo(lifecycle, key, 0f)
    }


}