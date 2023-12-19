package com.raveendra.finalproject_binar.presentation.detailcourse

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.provider.Settings
import android.view.OrientationEventListener
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityDetailCourseBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern

class DetailCourseActivity : AppCompatActivity() {
    private val binding: ActivityDetailCourseBinding by lazy {
        ActivityDetailCourseBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailViewModel by viewModel()

    private var youTubePlayer: YouTubePlayer? = null
    private var isFullscreen = false
    private val windowInsetsController: WindowInsetsControllerCompat by lazy {
        WindowCompat.getInsetsController(window, window.decorView)
    }

    private var previousOrientation: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        initYoutube()
        val orientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                val newOrientation = when (orientation) {
                    in 0..44 -> 0
                    in 45..134 -> 1
                    in 135..224 -> 2
                    in 225..314 -> 3
                    in 315..359 -> 0
                    else -> ORIENTATION_UNKNOWN
                }
                if (newOrientation != previousOrientation && !isFullscreen) {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
                }
                previousOrientation = newOrientation
            }
        }
        val autoRotationOn = Settings.System.getInt(
            contentResolver,
            Settings.System.ACCELEROMETER_ROTATION, 0
        ) == 1
        if (autoRotationOn) {
            orientationEventListener.enable()
        } else {
            orientationEventListener.disable()
        }
        sectionPageFragment()
    }

    private fun extractVideoIdFromUrl(videoUrl: String): String? {
        val pattern =
            "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/|youtu.be\\/|v\\/|\\/\\?v=|\\/videos\\/|\\/embed\\/|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed&v=|%2Fvideos%2F|%2Fvideos%2F|watch\\?v=|v=|v/|/videos/|watch\\?v=|embed\\/|youtu.be\\/|v=|v\\/|watch\\?v=|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed&v=|%2Fvideos%2F|%2Fvideos%2F|%2Fvideos%2F|%2Fvideos%2F)([^#\\&\\?\\n]*)"
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(videoUrl)
        return if (matcher.find()) {
            matcher.group()
        } else {
            null
        }
    }


    private fun initYoutube() {
        val iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(1) // enable full screen button
            .build()
        binding.playerView.apply {
            enableAutomaticInitialization = false
            addFullscreenListener(object : FullscreenListener {
                override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                    enterFullScreen(fullscreenView)
                }

                override fun onExitFullscreen() {
                    exitFullscreen()
                }

            })
            initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    this@DetailCourseActivity.youTubePlayer = youTubePlayer
                    observeDataVideo()
                }
            }, iFramePlayerOptions)
        }
        lifecycle.addObserver(binding.playerView)
    }

    private fun observeDataVideo() {
        viewModel.contentUrl.observe(this){result ->
            val urlVideo =extractVideoIdFromUrl(result)
            playVideo(urlVideo)
        }
    }

    private fun playVideo(videoUrl: String?) {
        if (!videoUrl.isNullOrBlank()) {
            youTubePlayer?.loadVideo(videoUrl, 0f)
        }
    }

    private fun exitFullscreen() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        isFullscreen = false
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        binding.titleItem.isVisible = true
        binding.playerView.isVisible = true
        binding.viewPager2.isVisible = true
        binding.fullScreenView.apply {
            isVisible = false
            removeAllViews()
        }
    }

    private fun enterFullScreen(view: View) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
        isFullscreen = true
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        binding.titleItem.isVisible = false
        binding.playerView.isVisible = false
        binding.viewPager2.isVisible = false
        binding.fullScreenView.apply {
            isVisible = true
            addView(view)
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