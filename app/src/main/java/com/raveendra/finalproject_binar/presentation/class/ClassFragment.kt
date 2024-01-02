package com.raveendra.finalproject_binar.presentation.`class`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.FragmentClassBinding
import com.raveendra.finalproject_binar.presentation.`class`.class_adapter.ClassAdapter
import com.raveendra.finalproject_binar.presentation.course.SwipeRefreshList
import com.raveendra.finalproject_binar.presentation.detailcourse.DetailCourseActivity
import com.raveendra.finalproject_binar.utils.ApiException
import com.raveendra.finalproject_binar.utils.NoInternetException
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassFragment : BaseFragment<FragmentClassBinding>() {

    private val viewModel: ClassViewModel by viewModel()

    private val adapterClass: ClassAdapter by lazy {
        ClassAdapter {
            DetailCourseActivity.navigate(requireContext(),it.courseUserId, true)
        }
    }
    private var classStatus: String? = null


    private val swipeRefreshListener = SwipeRefreshList {
        viewModel.getClass(status = classStatus)
    }
    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentClassBinding
        get() = FragmentClassBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
        setupSwipeRefreshLayout()
        setClickChips()
    }

    private fun setupRecyclerView() {
        binding.rvList.adapter = adapterClass
        viewModel.getClass()
    }
    private fun observeData() {
        viewModel.course.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.chipGroupFilter.isVisible = true
                    binding.rvList.isVisible = true
                    binding.layoutStateCourse.root.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = false
                    binding.layoutStateCourse.ivNotFound.isVisible = false
                    it.payload?.let {
                        adapterClass.setData(it)
                    }
                    binding.swipeRefreshLayout.isRefreshing = false
                },
                doOnLoading = {
                    binding.shimmerView.startShimmer()
                    binding.shimmerView.isVisible = true
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
                    binding.chipGroupFilter.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.ivNotFound.isVisible = true
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.tvError.isVisible = true
                    binding.swipeRefreshLayout.isRefreshing = false
                },
                doOnError = {error ->
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.chipGroupFilter.isVisible = false
                    binding.rvList.isVisible = false
                    binding.layoutStateCourse.root.isVisible = true
                    binding.layoutStateCourse.pbLoading.isVisible = false
                    binding.layoutStateCourse.ivNotFound.isVisible = true
                    if (error.exception is ApiException) {
                        if (error.exception.httpCode == 500) {
                            binding.layoutStateCourse.root.isVisible = true
                            binding.layoutStateCourse.ivNotFound.isVisible = true
                            binding.layoutStateCourse.tvError.text = getString(R.string.label_error_not_login_general)
                            binding.layoutStateCourse.tvError.isVisible = true
                        } else {
                            val exceptionMessage = error.exception.getParsedError()?.message
                            if (!exceptionMessage.isNullOrBlank()) {
                                Toast.makeText(
                                    requireContext(),
                                    exceptionMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    } else if (error.exception is NoInternetException) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.label_error_no_internet),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getClass(status = classStatus)
    }

    private fun setupSwipeRefreshLayout() {
        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener)
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.primary_dark_blue_06)
        )
    }
    private fun setClickChips() {
        binding.chip1.setOnClickListener {
            classStatus = null
            viewModel.getClass(status = classStatus)
        }
        binding.chip2.setOnClickListener {
            classStatus = "inProgress"
            viewModel.getClass(status = classStatus)
        }
        binding.chip3.setOnClickListener {
            classStatus = "Selesai"
            viewModel.getClass(status = classStatus)
        }
    }
}