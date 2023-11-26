package com.raveendra.finalproject_binar.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.raveendra.finalproject_binar.data.dummy.DummyCategoryCourseDataSource
import com.raveendra.finalproject_binar.data.dummy.DummyCategoryCourseDataSourceImpl
import com.raveendra.finalproject_binar.data.dummy.DummyPopularCourseDataSource
import com.raveendra.finalproject_binar.data.dummy.DummyPopularCourseDataSourceImpl
import com.raveendra.finalproject_binar.databinding.FragmentHomeBinding
import com.raveendra.finalproject_binar.model.CourseCategory
import com.raveendra.finalproject_binar.model.PopularCourse
import com.raveendra.finalproject_binar.presentation.home.adapter.AdapterCourseCategory
import com.raveendra.finalproject_binar.presentation.home.adapter.AdapterPopularCourse
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment  : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModel()

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCategoryCourse()
        showPopularCourse()
    }

    private fun showCategoryCourse() {
        val adapterCourseCategory = AdapterCourseCategory()
        binding.rvCategoryCourse.adapter = adapterCourseCategory
        binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireContext(), 2 )
        val dummyCategoryCourseDataSource: DummyCategoryCourseDataSource = DummyCategoryCourseDataSourceImpl()
        val courseList: List<CourseCategory> = dummyCategoryCourseDataSource.getCategoryCourse()
        adapterCourseCategory.setData(courseList)

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