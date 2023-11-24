package com.raveendra.finalproject_binar.presentation.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raveendra.finalproject_binar.data.dummy.DummyCourseDataSourceImpl
import com.raveendra.finalproject_binar.databinding.FragmentCourseBinding
import com.raveendra.finalproject_binar.model.Course
import com.raveendra.finalproject_binar.presentation.course.adapter.CourseAdapter
import com.raveendra.finalproject_binar.presentation.course.adapter.CourseTypeAdapter
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class CourseFragment: BaseFragment<FragmentCourseBinding>() {

    private val viewModel: CourseViewModel by viewModel()

    private val adapterCourse: CourseAdapter by lazy {
        CourseAdapter(CourseTypeAdapter.PREMIUM) { course: Course ->

        }
    }

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCourseBinding
        get() = FragmentCourseBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvList.adapter = adapterCourse
        adapterCourse.setData(DummyCourseDataSourceImpl().getCourseList())
    }

    private fun setClickChips(){
    }
}