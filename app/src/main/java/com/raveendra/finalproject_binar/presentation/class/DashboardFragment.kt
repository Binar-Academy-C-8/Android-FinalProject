package com.raveendra.finalproject_binar.presentation.`class`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.raveendra.finalproject_binar.data.dummy.DummyCategoryCourseDataSource
import com.raveendra.finalproject_binar.data.dummy.DummyCategoryCourseDataSourceImpl
import com.raveendra.finalproject_binar.data.dummy.DummyCourseDataSourceImpl
import com.raveendra.finalproject_binar.databinding.FragmentDashboardBinding
import com.raveendra.finalproject_binar.model.CourseCategory
import com.raveendra.finalproject_binar.presentation.classdadapter.DashboardAdapter
import com.raveendra.finalproject_binar.presentation.home.adapter.AdapterCourseCategory
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val viewModel: DashboardViewModel by viewModel()

    private val adapterDashboard: DashboardAdapter by lazy {
        DashboardAdapter() {

        }
    }

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDashboardBinding
        get() = FragmentDashboardBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        showCategoryCourse()
    }

    private fun setupRecyclerView() {
        binding.rvList.adapter = adapterDashboard
        adapterDashboard.setData(DummyCourseDataSourceImpl().getCourseList())
    }

    private fun showCategoryCourse() {
        val adapterCourseCategory = AdapterCourseCategory()
        binding.rvCategoryCourse.adapter = adapterCourseCategory
        binding.rvCategoryCourse.layoutManager = GridLayoutManager(requireContext(), 2 )
        val dummyCategoryCourseDataSource: DummyCategoryCourseDataSource = DummyCategoryCourseDataSourceImpl()
        val courseList: List<CourseCategory> = dummyCategoryCourseDataSource.getCategoryCourse()
        adapterCourseCategory.setData(courseList)

    }


}