package com.raveendra.finalproject_binar.presentation.seeallpopularcourse

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.raveendra.finalproject_binar.databinding.ActivitySeeAllPopularCourseBinding
import com.raveendra.finalproject_binar.presentation.detailcourse.DetailCourseActivity
import com.raveendra.finalproject_binar.presentation.payment.payment_summary.PaymentSummaryActivity
import com.raveendra.finalproject_binar.presentation.seeallpopularcourse.adapter.AdapterSeeAllPopularCourse
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeeAllPopularCourseActivity : BaseViewModelActivity<SeeAllPopularCourseViewModel, ActivitySeeAllPopularCourseBinding>() {
    companion object {
        fun navigate(context: Context) = with(context) {
            startActivity(
                Intent(
                    this,
                    SeeAllPopularCourseActivity::class.java
                )
            )
        }
    }

    override val viewModel: SeeAllPopularCourseViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivitySeeAllPopularCourseBinding
        get() = ActivitySeeAllPopularCourseBinding::inflate

    override fun setupViews() {
        getData()
        setClickListener()
    }

    private val seeAllPopularCoursesAdapter: AdapterSeeAllPopularCourse by lazy {
        AdapterSeeAllPopularCourse(itemClick = {
            startActivity(
                Intent(
                    this,
                    DetailCourseActivity::class.java
                )
            )
        }, buttonClick = {
            it.id?.let { id ->
                PaymentSummaryActivity.navigate(
                    this,
                    id
                )
            }
        })
    }

    override fun setupObservers() {
        viewModel.course.observe(this) {
            it.proceedWhen(doOnSuccess = {
                binding.shimmerViewAllCourse.stopShimmer()
                binding.shimmerViewAllCourse.isVisible = false
                binding.layoutStateSeeAllPopularCourse.tvError.isVisible = false
                binding.layoutStateSeeAllPopularCourse.ivNotFound.isVisible = false
                binding.rvSeeAllPopularCourse.apply {
                    isVisible = true
                    adapter = seeAllPopularCoursesAdapter
                    itemAnimator = null
                }
                binding.rvSeeAllPopularCourse.smoothScrollToPosition(0)
                it.payload?.let { data -> seeAllPopularCoursesAdapter.setData(data) }
                seeAllPopularCoursesAdapter.refreshList()
            }, doOnLoading = {
                binding.shimmerViewAllCourse.startShimmer()
                binding.shimmerViewAllCourse.isVisible = true
                binding.rvSeeAllPopularCourse.isVisible = false
            }, doOnError = {
                binding.shimmerViewAllCourse.stopShimmer()
                binding.shimmerViewAllCourse.isVisible = false
                binding.layoutStateSeeAllPopularCourse.root.isVisible = true
                binding.layoutStateSeeAllPopularCourse.pbLoading.isVisible = false
                binding.layoutStateSeeAllPopularCourse.ivNotFound.isVisible = true
                binding.rvSeeAllPopularCourse.isVisible = false
            }, doOnEmpty = {
                binding.shimmerViewAllCourse.stopShimmer()
                binding.shimmerViewAllCourse.isVisible = false
                binding.layoutStateSeeAllPopularCourse.root.isVisible = true
                binding.layoutStateSeeAllPopularCourse.pbLoading.isVisible = false
                binding.layoutStateSeeAllPopularCourse.tvError.isVisible = false
                binding.layoutStateSeeAllPopularCourse.ivNotFound.isVisible = true
                binding.rvSeeAllPopularCourse.isVisible = false
            })
        }
    }

    private fun getData() {
        viewModel.getCourses()
    }

    private fun setClickListener(){
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

}