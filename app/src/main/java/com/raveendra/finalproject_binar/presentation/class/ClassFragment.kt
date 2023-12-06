package com.raveendra.finalproject_binar.presentation.`class`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raveendra.finalproject_binar.data.dummy.DummyCategoryCourseDataSourceImpl
import com.raveendra.finalproject_binar.data.dummy.DummyCourseDataSourceImpl
import com.raveendra.finalproject_binar.databinding.FragmentDashboardBinding
import com.raveendra.finalproject_binar.presentation.`class`.class_adapter.DashboardAdapter
import com.raveendra.finalproject_binar.presentation.`class`.class_adapter.DashboardCategoryAdapter
import com.c8.core.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassFragment : BaseFragment<FragmentDashboardBinding>() {

    private val viewModel: ClassViewModel by viewModel()

    private val adapterDashboard: DashboardAdapter by lazy {
        DashboardAdapter() {

        }
    }

    private val adapterCategoryDashboard: DashboardCategoryAdapter by lazy {
        DashboardCategoryAdapter() {

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
        binding.rvCategoryCourse.adapter = adapterCategoryDashboard
        adapterCategoryDashboard.setData(DummyCategoryCourseDataSourceImpl().getCategoryCourse())

    }


}