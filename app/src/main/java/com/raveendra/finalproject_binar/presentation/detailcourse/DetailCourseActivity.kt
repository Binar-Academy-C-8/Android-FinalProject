package com.raveendra.finalproject_binar.presentation.detailcourse

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.c8.core.utils.proceedWhen
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityDetailCourseBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCourseActivity() : AppCompatActivity() {
    private val binding: ActivityDetailCourseBinding by lazy {
        ActivityDetailCourseBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailViewModel by viewModel()

    private var youTubePlayer : YouTubePlayer? = null
    private var isFullscreen = false
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isFullscreen) {
                // if the player is in fullscreen, exit fullscreen
                youTubePlayer?.toggleFullscreen()
            } else {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        setVideoFullScreen()
        initYoutube()
        sectionPageFragment()
    }



    private fun initYoutube() {
        binding.playerView.enableAutomaticInitialization = false
        binding.playerView.initialize(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                this@DetailCourseActivity.youTubePlayer = youTubePlayer
                observeDataVideo()
            }
        })
        lifecycle.addObserver(binding.playerView)
    }

    private fun observeDataVideo() {
        viewModel.videoUrl.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    val resultVideoUrl = result.payload
                    Toast.makeText(this, "$resultVideoUrl", Toast.LENGTH_SHORT).show()
                    playVideo(resultVideoUrl)
                },
                doOnError = {
                    val errorMessage = it.message
                    Toast.makeText(this, "Error $errorMessage", Toast.LENGTH_SHORT).show()
                }
            )
        }

    }

    private fun playVideo(videoUrl: String?) {
        if (!videoUrl.isNullOrBlank()) {
            youTubePlayer?.loadVideo(videoUrl,0f)
        }
    }

    private fun loadVideos(videoUrl: String?) {
        if (!videoUrl.isNullOrBlank()) {
            val youTubePlayerView = binding.playerView
            lifecycle.addObserver(youTubePlayerView)

            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {

                    val videoId = videoUrl

                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })
        }
    }


    private fun sectionPageFragment() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager2.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabsLayout, binding.viewPager2) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}