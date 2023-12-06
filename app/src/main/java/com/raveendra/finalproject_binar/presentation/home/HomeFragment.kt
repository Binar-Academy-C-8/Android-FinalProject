package com.raveendra.finalproject_binar.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.c8.core.utils.base.BaseFragment
import com.c8.core.utils.proceedWhen
import com.raveendra.finalproject_binar.data.dummy.DummyPopularCourseDataSource
import com.raveendra.finalproject_binar.data.dummy.DummyPopularCourseDataSourceImpl
import com.raveendra.finalproject_binar.databinding.FragmentHomeBinding
import com.raveendra.finalproject_binar.model.PopularCourse
import com.raveendra.finalproject_binar.presentation.home.adapter.AdapterPopularCourse
import com.raveendra.finalproject_binar.presentation.home.adapter.CategoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment  : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModel()

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {

        }
    }

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPopularCourse()
        getData()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.categories.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
//                binding.layoutStateCategory.root.isVisible = false
//                binding.layoutStateCategory.pbLoading.isVisible = false
//                binding.layoutStateCategory.tvError.isVisible = false
                binding.rvCategoryCourse.apply {
                    isVisible = true
                    adapter = categoryAdapter
                    layoutManager = GridLayoutManager(requireContext(), 2 )
                }
                it.payload?.let { data -> categoryAdapter.setData(data) }
                categoryAdapter.refreshList()
            }, doOnLoading = {
//                binding.layoutStateCategory.root.isVisible = true
//                binding.layoutStateCategory.pbLoading.isVisible = true
//                binding.layoutStateCategory.tvError.isVisible = false
//                binding.rvCategory.isVisible = false
            }, doOnError = {
//                binding.layoutStateCategory.root.isVisible = true
//                binding.layoutStateCategory.pbLoading.isVisible = false
//                binding.layoutStateCategory.tvError.isVisible = true
//                binding.layoutStateCategory.tvError.text = it.exception?.message.orEmpty()
//                binding.rvCategory.isVisible = false
            })
        }
        viewModel.course.observe(viewLifecycleOwner) {
//            it.proceedWhen(doOnSuccess = {
//                binding.layoutStateProduct.root.isVisible = false
//                binding.layoutStateProduct.pbLoading.isVisible = false
//                binding.layoutStateProduct.tvError.isVisible = false
//                binding.rvProductList.apply {
//                    isVisible = true
//                    adapter = productAdapter
//                    itemAnimator = null
//                }
//                binding.rvProductList.smoothScrollToPosition(0)
//                it.payload?.let { data -> productAdapter.submitData(data) }
//            }, doOnLoading = {
//                binding.layoutStateProduct.root.isVisible = true
//                binding.layoutStateProduct.pbLoading.isVisible = true
//                binding.layoutStateProduct.tvError.isVisible = false
//                binding.rvProductList.isVisible = false
//            }, doOnError = {
//                binding.layoutStateProduct.root.isVisible = true
//                binding.layoutStateProduct.pbLoading.isVisible = false
//                binding.layoutStateProduct.tvError.isVisible = true
//                binding.layoutStateProduct.tvError.text = it.exception?.message.orEmpty()
//                binding.rvProductList.isVisible = false
//            }, doOnEmpty = {
//                binding.layoutStateProduct.root.isVisible = true
//                binding.layoutStateProduct.pbLoading.isVisible = false
//                binding.layoutStateProduct.tvError.isVisible = true
//                binding.layoutStateProduct.tvError.text = "Product not found"
//                binding.rvProductList.isVisible = false
//            })
        }
    }

    private fun getData() {
        viewModel.getCategories()
        viewModel.getProducts()
    }

    private fun showPopularCourse() {
        val adapterPopularCourse = AdapterPopularCourse()
        binding.rvPopularCourse.adapter = adapterPopularCourse
        binding.rvPopularCourse.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false )
        val dummyPopularCourseDataSource: DummyPopularCourseDataSource = DummyPopularCourseDataSourceImpl()
        val popularCourseList: List<PopularCourse> = dummyPopularCourseDataSource.getPopularCourse()
        adapterPopularCourse.setData(popularCourseList)
    }
}