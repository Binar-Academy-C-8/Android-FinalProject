package com.raveendra.finalproject_binar.utils


import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.utils.FadeViewHelper
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.customui.views.YouTubePlayerSeekBarListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.raveendra.finalproject_binar.R


/**
 *hrahm,27/11/2023, 09:13
 **/
class CustomPlayerUiController internal constructor(
    controlsUi: View,
    youTubePlayer: YouTubePlayer,
    youTubePlayerView: YouTubePlayerView
) :
    AbstractYouTubePlayerListener() {
    private val playerTracker: YouTubePlayerTracker
    private val youTubePlayer: YouTubePlayer
    private val youTubePlayerView: YouTubePlayerView
    private var isFullScreen = false

    init {
        this.youTubePlayer = youTubePlayer
        this.youTubePlayerView = youTubePlayerView
        playerTracker = YouTubePlayerTracker()
        youTubePlayer.addListener(playerTracker)
        initViews(controlsUi)
    }

    private fun initViews(view: View) {
        val container = view.findViewById<View>(R.id.container)
        val relativeLayout = view.findViewById<RelativeLayout>(R.id.root)
        val seekBar = view.findViewById<YouTubePlayerSeekBar>(R.id.playerSeekbar)
        val pausePlay = view.findViewById<ImageButton>(R.id.pausePlay)
        val fullScreen = view.findViewById<ImageButton>(R.id.toggleFullScreen)

        youTubePlayer.addListener(seekBar)
        seekBar.youtubePlayerSeekBarListener = object : YouTubePlayerSeekBarListener {
            override fun seekTo(v: Float) {
                youTubePlayer.seekTo(v)
            }
        }
        pausePlay.setOnClickListener {
            if (playerTracker.state == PlayerState.PLAYING) {
                pausePlay.setImageResource(R.drawable.baseline_pause_24)
                youTubePlayer.pause()
            } else {
                pausePlay.setImageResource(R.drawable.ic_play)
                youTubePlayer.play()
            }
        }
        fullScreen.setOnClickListener {
            if (isFullScreen) {
                youTubePlayerView.wrapContent()
            } else {
                youTubePlayerView.matchParent()
            }
            isFullScreen = !isFullScreen
        }
        val fadeViewHelper = FadeViewHelper(container)
        fadeViewHelper.animationDuration = FadeViewHelper.DEFAULT_ANIMATION_DURATION
        fadeViewHelper.fadeOutDelay = FadeViewHelper.DEFAULT_FADE_OUT_DELAY
        youTubePlayer.addListener(fadeViewHelper)
        relativeLayout.setOnClickListener { fadeViewHelper.toggleVisibility() }
        container.setOnClickListener { fadeViewHelper.toggleVisibility() }
    }
}