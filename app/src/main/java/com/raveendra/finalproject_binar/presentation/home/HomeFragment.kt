package com.raveendra.finalproject_binar.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.raveendra.finalproject_binar.databinding.FragmentHomeBinding
import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.raveendra.finalproject_binar.presentation.MainActivity
import com.raveendra.finalproject_binar.presentation.detailcourse.DetailCourseActivity
import com.raveendra.finalproject_binar.presentation.home.adapter.AdapterPopularCourse
import com.raveendra.finalproject_binar.presentation.home.adapter.CategoryAdapter
import com.raveendra.finalproject_binar.presentation.payment.payment_summary.PaymentSummaryActivity
import com.raveendra.finalproject_binar.presentation.seeallpopularcourse.SeeAllPopularCourseActivity
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModel()

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            viewModel.getCourses(it.id)
        }
    }

    private val popularCourseAdapter: AdapterPopularCourse by lazy {
        AdapterPopularCourse(
            itemClick = {
                startActivity(
                    Intent(
                        requireContext(),
                        DetailCourseActivity::class.java
                    )
                )
            },
            buttonClick = {
                it.id?.let { id ->
                    PaymentSummaryActivity.navigate(
                        requireContext(),
                        id
                    )
                }
            })
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
                binding.shimmerView.stopShimmer()
                binding.shimmerView.isVisible = false
                binding.layoutStateCategory.root.isVisible = false
                binding.layoutStateCategory.tvError.isVisible = false
                binding.rvCategoryCourse.apply {
                    isVisible = true
                    adapter = categoryAdapter
                }
                it.payload?.let { data ->
                    val allData = data.toMutableList()
                    allData.add(
                        0, CategoryDomain(
                            categoryName = "Show All",
                            createdAt = "",
                            id = 0,
                            image = "",
                            updatedAt = ""
                        )
                    )
                    categoryAdapter.setData(allData)
                }
                categoryAdapter.refreshList()
            }, doOnLoading = {
                binding.shimmerView.startShimmer()
                binding.shimmerView.isVisible = true
                binding.layoutStateCategory.tvError.isVisible = false
                binding.rvCategoryCourse.isVisible = false
            }, doOnError = {
                binding.layoutStateCategory.root.isVisible = true
                binding.layoutStateCategory.tvError.isVisible = true
                binding.layoutStateCategory.tvError.text = it.exception?.message.orEmpty()
                binding.rvCategoryCourse.isVisible = false
            })
        }
        viewModel.course.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.shimmerViewCourse.stopShimmer()
                binding.shimmerViewCourse.isVisible = false
                binding.layoutStatePopularCourse.tvError.isVisible = false
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
                binding.shimmerViewCourse.isVisible = true
                binding.rvPopularCourse.isVisible = false
            }, doOnError = {
                binding.layoutStatePopularCourse.root.isVisible = true
                binding.layoutStatePopularCourse.pbLoading.isVisible = false
                binding.layoutStatePopularCourse.tvError.isVisible = true
                binding.layoutStatePopularCourse.tvError.text = it.exception?.message.orEmpty()
                binding.rvPopularCourse.isVisible = false
            }, doOnEmpty = {
                binding.layoutStatePopularCourse.root.isVisible = true
                binding.layoutStatePopularCourse.pbLoading.isVisible = false
                binding.layoutStatePopularCourse.tvError.isVisible = true
                binding.layoutStatePopularCourse.tvError.text = "Course not found"
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
            SeeAllPopularCourseActivity.navigate(requireContext())
        }
    }



}