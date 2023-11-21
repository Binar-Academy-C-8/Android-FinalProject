package com.raveendra.finalproject_binar.presentation.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.FragmentCourseBinding
import com.raveendra.finalproject_binar.presentation.account.AccountViewModel
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class CourseFragment: BaseFragment<FragmentCourseBinding>() {

    private val viewModel: CourseViewModel by viewModel()

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCourseBinding
        get() = FragmentCourseBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}