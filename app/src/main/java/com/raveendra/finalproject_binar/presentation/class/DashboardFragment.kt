package com.raveendra.finalproject_binar.presentation.`class`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raveendra.finalproject_binar.databinding.FragmentDashboardBinding
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val viewModel: DashboardViewModel by viewModel()

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDashboardBinding
        get() = FragmentDashboardBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}