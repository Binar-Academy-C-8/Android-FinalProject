package com.raveendra.finalproject_binar.presentation.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.FragmentCourseBinding
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.presentation.course.adapter.CourseAdapter
import com.raveendra.finalproject_binar.presentation.popup.FilterCourseBottomSheet
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel


class CourseFragment : BaseFragment<FragmentCourseBinding>()  {

    private val viewModel: CourseViewModel by viewModel()

    private var filterCourseBottomSheet: FilterCourseBottomSheet? = null

    var courseType: String? = null

    private val adapterCourse: CourseAdapter by lazy {
        CourseAdapter { course: CourseDomain ->

        }
    }

    private val swipeRefreshListener = SwipeRefreshList {
        viewModel.getCourse(courseType)
    }

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCourseBinding
        get() = FragmentCourseBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getData()
        observeData()
        setClickChips()
        setupSwipeRefreshLayout()
        setupFilter()
    }

    private fun observeData() {
        viewModel.course.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.courseChipShimmer.stopShimmer()
                    binding.courseChipShimmer.isVisible = false
                    binding.chipGroupFilter.isVisible = true
                    binding.rvList.isVisible = true
                    binding.layoutStateCourse.root.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.ivNotFound.isVisible = false
                    it.payload?.let {
                        adapterCourse.setData(it)
                    }
                    binding.swipeRefreshLayout.isRefreshing = false
                },
                doOnLoading = {
                    binding.shimmerView.startShimmer()
                    binding.shimmerView.isVisible = true
                    binding.courseChipShimmer.startShimmer()
                    binding.courseChipShimmer.isVisible = true
                    binding.rvList.isVisible = false
                    binding.chipGroupFilter.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.root.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.ivNotFound.isVisible = false
                },
                doOnEmpty = {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.rvList.isVisible = false
                    binding.courseChipShimmer.stopShimmer()
                    binding.courseChipShimmer.isVisible = false
                    binding.chipGroupFilter.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.ivNotFound.isVisible = true
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.swipeRefreshLayout.isRefreshing = false
                },
                doOnError = {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.courseChipShimmer.stopShimmer()
                    binding.courseChipShimmer.isVisible = false
                    binding.chipGroupFilter.isVisible = false
                    binding.rvList.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.ivNotFound.isVisible = true
                    it.exception?.message.toString()
                    Toast.makeText(
                        requireContext(),
                        it.exception?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    binding.swipeRefreshLayout.isRefreshing = false
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
            courseType = null
        }
        binding.chip2.setOnClickListener {
            viewModel.getCourse(courseType = "Premium")
            courseType = "Premium"
        }
        binding.chip3.setOnClickListener {
            viewModel.getCourse(courseType = "Free")
            courseType = "Free"
        }
    }
    private fun getData() {
        viewModel.getCourse()
    }

    private fun setupSwipeRefreshLayout() {
        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener)
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.primary_dark_blue_06)
        )
    }
    private fun setupFilter() = with(binding){
        filterCourseBottomSheet = FilterCourseBottomSheet(
            context = requireContext()
        )
        tvFilter.setOnClickListener {
            filterCourseBottomSheet?.show(
                requireActivity().supportFragmentManager,
                CourseFragment::class.java.simpleName
            )
        }
    }
}