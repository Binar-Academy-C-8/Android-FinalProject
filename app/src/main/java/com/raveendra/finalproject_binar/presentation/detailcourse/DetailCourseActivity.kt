package com.raveendra.finalproject_binar.presentation.detailcourse

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityDetailCourseBinding
import com.raveendra.finalproject_binar.utils.DataItem
import com.raveendra.finalproject_binar.utils.HeaderItem
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern

class DetailCourseActivity : BaseViewModelActivity<DetailViewModel, ActivityDetailCourseBinding>() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_COURSE_ID = "EXTRA_COURSE_ID"
        fun navigate(context: Context, courseId: Int) = with(context) {
            startActivity(
                Intent(
                    this,
                    DetailCourseActivity::class.java
                ).putExtra(EXTRA_COURSE_ID, courseId)
            )
        }
    }

    private val courseIdExtra by lazy {
        intent.getIntExtra(EXTRA_COURSE_ID, 0)
    }

    override val viewModel: DetailViewModel by viewModel()

    private val adapterGropie: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    override val bindingInflater: (LayoutInflater) -> ActivityDetailCourseBinding
        get() = ActivityDetailCourseBinding::inflate

    private var youTubePlayer: YouTubePlayer? = null
    private var isFullscreen = false
    private val windowInsetsController: WindowInsetsControllerCompat by lazy {
        WindowCompat.getInsetsController(window, window.decorView)
    }

    private var previousOrientation: Int = -1

    override fun setupViews() = with(binding) {
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        initYoutube()
        val orientationEventListener =
            object : OrientationEventListener(this@DetailCourseActivity) {
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
        /*sectionPageFragment()*/

        viewModel.getVideos(courseIdExtra)

        prevPage()
        showDesc()

    }

    override fun setupObservers() {

        viewModel.detailData.observe(this) {
            it.proceedWhen(
                doOnSuccess = { success ->
                    binding.shimmerViewTitle.stopShimmer()
                    binding.shimmerViewTitle.isVisible = false
                    binding.shimmerViewCourse.stopShimmer()
                    binding.shimmerViewCourse.isVisible = false
                    success.payload?.data?.chapters?.firstOrNull()?.contents?.firstOrNull()?.contentUrl?.let { firstUrl ->
                        viewModel.getContentUrl(
                            firstUrl
                        )
                    }

                    val detailAbout = success.payload?.data
                    binding.tvAbout.text = detailAbout?.aboutCourse
                    binding.tvIntendedFor.text = detailAbout?.intendedFor

                    binding.rvPage.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = adapterGropie
                    }

                    val section = success.payload?.data?.chapters?.map { chapters ->
                        val section = Section()
                        section.setHeader(HeaderItem(chapters?.chapterTitle.orEmpty()))

                        val dataSection = chapters?.contents?.map { data ->
                            data?.let { contents ->
                                DataItem(contents) { data ->
                                    viewModel.getContentUrl(data.contentUrl)
                                }
                            }
                        }
                        dataSection?.let { it1 -> section.addAll(it1) }
                        section
                    }
                    section?.let { data -> adapterGropie.addAll(data) }

                }, doOnLoading = {
                    binding.shimmerViewTitle.startShimmer()
                    binding.shimmerViewTitle.isVisible = true
                    binding.shimmerViewCourse.startShimmer()
                    binding.shimmerViewCourse.isVisible = true
                },
                doOnError = { error ->
                    error.message.toString()
                }
            )
        }
    }

    private fun prevPage() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showDesc() {
        binding.btDescription.setOnClickListener {
            binding.llAboutCourse.isVisible = true
            binding.btDescription.isVisible = false
            binding.btDescription2.isVisible = true
        }

        binding.btDescription2.setOnClickListener {
            binding.llAboutCourse.isVisible = false
            binding.btDescription2.isVisible = false
            binding.btDescription.isVisible = true
        }
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
        viewModel.contentUrl.observe(this) { result ->
            val urlVideo = extractVideoIdFromUrl(result)
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
        binding.playerView.isVisible = true
        /*binding.viewPager2.isVisible = true*/
        binding.llAboutCourse.isVisible = true
        binding.tvAbout.isVisible = true
        binding.tvIntendedFor.isVisible = true
        binding.rvPage.isVisible = true
        binding.fullScreenView.apply {
            isVisible = false
            removeAllViews()
        }
    }

    private fun enterFullScreen(view: View) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
        isFullscreen = true
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        binding.playerView.isVisible = false
        binding.llAboutCourse.isVisible = false
        binding.tvAbout.isVisible = false
        binding.tvIntendedFor.isVisible = false
        binding.rvPage.isVisible = false
        /*binding.viewPager2.isVisible = false*/
        binding.fullScreenView.apply {
            isVisible = true
            addView(view)
        }
    }


    /*private fun sectionPageFragment() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailCourseActivity)
        binding.viewPager2.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabsLayout, binding.viewPager2) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }*/

//

}