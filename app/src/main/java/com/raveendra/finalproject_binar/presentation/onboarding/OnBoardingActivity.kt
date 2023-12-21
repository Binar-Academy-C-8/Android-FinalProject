package com.raveendra.finalproject_binar.presentation.onboarding

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityOnBoardingBinding
import com.raveendra.finalproject_binar.presentation.MainActivity
import com.raveendra.finalproject_binar.presentation.auth.login.LoginActivity
import com.raveendra.finalproject_binar.presentation.onboarding.adapter.OnBoardingPagerAdapter
import com.raveendra.finalproject_binar.utils.highLightWord

class OnBoardingActivity : AppCompatActivity() {

    private val binding: ActivityOnBoardingBinding by lazy {
        ActivityOnBoardingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListeners()
        setupViewPager()
        createPageIndicator()
    }

    private fun setClickListeners() {
        binding.tvHaveAccount.highLightWord(getString(R.string.text_highlight_have_account)) {
            LoginActivity.navigate(this@OnBoardingActivity)
        }

    }

    private fun setupViewPager() {
        val viewPager: ViewPager2 = binding.viewPager2
        val adapter = OnBoardingPagerAdapter(this)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == adapter.itemCount - 1) {
                    binding.ibNext.setOnClickListener{
                        MainActivity.navigate(this@OnBoardingActivity)
                    }
                } else {
                    binding.ibNext.setOnClickListener {
                        val currentItem = binding.viewPager2.currentItem
                        if (currentItem < (binding.viewPager2.adapter?.itemCount ?: (0 - 1))) {
                            binding.viewPager2.setCurrentItem(currentItem + 1, true)
                        }
                    }
                }
                updatePageIndicator(position)
            }
        })
    }

    private fun createPageIndicator() {
        val indicatorLayout: LinearLayout = binding.llIndicator
        val adapter = binding.viewPager2.adapter as OnBoardingPagerAdapter

        for (i in 0 until adapter.itemCount) {
            val dot = ImageView(this)
            dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_indicator_inactive))

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(16, 0, 16, 0)
            indicatorLayout.addView(dot, params)
        }

        updatePageIndicator(0)

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updatePageIndicator(position)
            }
        })
    }

    private fun updatePageIndicator(position: Int) {
        val indicatorLayout: LinearLayout = binding.llIndicator

        for (i in 0 until indicatorLayout.childCount) {
            val dot = indicatorLayout.getChildAt(i) as ImageView
            if (i == position) {
                dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_indicator_active))
            } else {
                dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_indicator_inactive))
            }
        }
    }
}
