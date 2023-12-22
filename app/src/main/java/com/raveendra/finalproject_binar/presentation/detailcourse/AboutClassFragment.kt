package com.raveendra.finalproject_binar.presentation.detailcourse

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raveendra.finalproject_binar.databinding.FragmentAboutClassBinding
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class AboutClassFragment : BaseFragment<FragmentAboutClassBinding>() {
    private val viewModel: DetailViewModel by activityViewModel()
    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAboutClassBinding
        get() = FragmentAboutClassBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        viewModel.detailData.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { success ->
                    val detailAbout = success.payload?.data
                    binding.tvAbout.text = detailAbout?.aboutCourse
                    binding.tvIntendedFor.text = detailAbout?.intendedFor
                },
                doOnLoading = {

                },
                doOnError = { error ->
                    Log.e("ListClassFragment", "Error: ${error.message}")
                }
            )
        }
    }
}