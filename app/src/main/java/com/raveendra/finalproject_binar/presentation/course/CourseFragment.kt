package com.raveendra.finalproject_binar.presentation.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.raveendra.finalproject_binar.data.dummy.DummyCourseFreeImpl
import com.raveendra.finalproject_binar.data.dummy.DummyCoursePremiumImpl
import com.raveendra.finalproject_binar.databinding.FragmentCourseBinding
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.presentation.course.adapter.CourseAdapter
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel


class CourseFragment : BaseFragment<FragmentCourseBinding>() {

    private val viewModel: CourseViewModel by viewModel()

    private val adapterCourse: CourseAdapter by lazy {
        CourseAdapter { course: CourseDomain ->

        }
    }

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCourseBinding
        get() = FragmentCourseBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
        setClickChips()
    }

    private fun observeData() {
        viewModel.course.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.courseChipShimmer.stopShimmer()
                    binding.courseChipShimmer.isVisible = false
                    binding.chipGroupFilter.isVisible = false
                    binding.rvList.isVisible = true
                    binding.layoutStateCourse.root.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.ivEmpty.isVisible = false
                    it.payload?.let {
                        adapterCourse.setData(it)
                    }
                },
                doOnLoading = {
                    binding.shimmerView.startShimmer()
                    binding.shimmerView.isVisible = true
                    binding.shimmerView.startShimmer()
                    binding.shimmerView.isVisible = true
                    binding.courseChipShimmer.isVisible = true
                    binding.courseChipShimmer.startShimmer()
                    binding.rvList.isVisible = false
                    binding.chipGroupFilter.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.root.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.ivEmpty.isVisible = false
                },
                doOnEmpty = {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.rvList.isVisible = false
                    binding.courseChipShimmer.stopShimmer()
                    binding.chipGroupFilter.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.ivNotFound.isVisible = true
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.ivEmpty.isVisible = true
                },
                doOnError = {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.courseChipShimmer.stopShimmer()
                    binding.chipGroupFilter.isVisible = false
                    binding.rvList.isVisible = false
                    binding.layoutStateCourse.tvError.error
                    it.exception?.message.toString()
                    it.exception?.message.toString()
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.ivEmpty.isVisible = true
                    it.exception?.message.toString()
                    Toast.makeText(
                        requireContext(),
                        it.exception?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        }
    }

    private fun setupRecyclerView() {
        binding.rvList.adapter = adapterCourse
        viewModel.getCourse()
    }

    private fun setClickChips() {
        binding.chip1.setOnClickListener {
            viewModel.getCourse(courseType = null)
        }
        binding.chip2.setOnClickListener {
            viewModel.getCourse(courseType = "Premium")
        }
        binding.chip3.setOnClickListener {
            viewModel.getCourse(courseType = "Free")
        }
    }
}