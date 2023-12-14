package com.raveendra.finalproject_binar.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.raveendra.finalproject_binar.databinding.FragmentHomeBinding
import com.raveendra.finalproject_binar.presentation.home.adapter.AdapterPopularCourse
import com.raveendra.finalproject_binar.presentation.home.adapter.CategoryAdapter
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment  : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModel()

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            viewModel.getCourses(it.categoryName)
        }
    }

    private val popularCourseAdapter: AdapterPopularCourse by lazy {
        AdapterPopularCourse {
        }
    }

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setupObservers()
        setOnClickListener()
    }

    private fun setupObservers() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                /*binding.layoutStateCategory.root.isVisible = false
                binding.layoutStateCategory.pbLoading.isVisible = false
                binding.layoutStateCategory.tvError.isVisible = false*/
                binding.shimmerView.stopShimmer()
                //Hide Shimmer view
                binding.shimmerView.isVisible = false
                binding.rvCategoryCourse.apply {
                    isVisible = true
                    adapter = categoryAdapter
                }
                it.payload?.let { data -> categoryAdapter.setData(data) }
                categoryAdapter.refreshList()
            }, doOnLoading = {
                binding.shimmerView.startShimmer()
                binding.shimmerView.isVisible = true/*
                binding.layoutStateCategory.root.isVisible = true
                binding.layoutStateCategory.pbLoading.isVisible = true
                binding.layoutStateCategory.tvError.isVisible = false*/
                binding.rvCategoryCourse.isVisible = false
            }, doOnError = {/*
                binding.layoutStateCategory.root.isVisible = true
                binding.layoutStateCategory.pbLoading.isVisible = false
                binding.layoutStateCategory.tvError.isVisible = true
                binding.layoutStateCategory.tvError.text = it.exception?.message.orEmpty()*/
                binding.rvCategoryCourse.isVisible = false
            })
        }
        viewModel.course.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {/*
                binding.layoutStatePopularCourse.root.isVisible = false
                binding.layoutStatePopularCourse.pbLoading.isVisible = false
                binding.layoutStatePopularCourse.tvError.isVisible = false*/
                binding.shimmerViewCourse.stopShimmer()
                //Hide Shimmer view
                binding.shimmerViewCourse.isVisible = false
                binding.rvPopularCourse.apply {
                    isVisible = true
                    adapter = popularCourseAdapter
                    itemAnimator = null
                }
                binding.rvPopularCourse.smoothScrollToPosition(0)
                it.payload?.let { data -> popularCourseAdapter.setData(data) }
                popularCourseAdapter.refreshList()
            }, doOnLoading = {
                binding.shimmerViewCourse.startShimmer()
                binding.shimmerViewCourse.isVisible = true/*
                binding.layoutStatePopularCourse.root.isVisible = true
                binding.layoutStatePopularCourse.pbLoading.isVisible = true
                binding.layoutStatePopularCourse.tvError.isVisible = false*/
                binding.rvPopularCourse.isVisible = false
            }, doOnError = {/*
                binding.layoutStatePopularCourse.root.isVisible = true
                binding.layoutStatePopularCourse.pbLoading.isVisible = false
                binding.layoutStatePopularCourse.tvError.isVisible = true
                binding.layoutStatePopularCourse.tvError.text = it.exception?.message.orEmpty()*/
                binding.rvPopularCourse.isVisible = false
            }, doOnEmpty = {/*
                binding.layoutStatePopularCourse.root.isVisible = true
                binding.layoutStatePopularCourse.pbLoading.isVisible = false
                binding.layoutStatePopularCourse.tvError.isVisible = true
                binding.layoutStatePopularCourse.tvError.text = "Course not found"*/
                binding.rvPopularCourse.isVisible = false
            })
        }
    }

    private fun getData() {
        viewModel.getCategories()
        viewModel.getCourses()
    }

    private fun setOnClickListener(){
        binding.tvViewAll.setOnClickListener{

        }
    }


}