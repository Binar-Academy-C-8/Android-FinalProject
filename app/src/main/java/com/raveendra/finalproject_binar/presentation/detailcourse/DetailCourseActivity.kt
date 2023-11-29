package com.raveendra.finalproject_binar.presentation.detailcourse

import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityDetailCourseBinding
import com.raveendra.finalproject_binar.utils.DataItem
import com.raveendra.finalproject_binar.utils.HeaderItem
import com.raveendra.finalproject_binar.utils.proceedWhen
import com.xwray.groupie.Section
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCourseActivity : AppCompatActivity() {
    private val binding: ActivityDetailCourseBinding by lazy {
        ActivityDetailCourseBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        sectionPageFragment()
        playVideos()
    }
    private fun playVideos() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.urlVideos.observe(this@DetailCourseActivity){
                if (!it.payload?.urlVideos.isNullOrEmpty()) {
                    val youTubePlayerView = binding.playerView
                    lifecycle.addObserver(youTubePlayerView)
                    val videoId = it.payload?.urlVideos.orEmpty()
                    youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(videoId, 0f)
                        }

                        override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                            super.onError(youTubePlayer, error)
                            Log.e("YouTubePlayer", "Error: ${error.name}")
                        }
                    })

                }
            }




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