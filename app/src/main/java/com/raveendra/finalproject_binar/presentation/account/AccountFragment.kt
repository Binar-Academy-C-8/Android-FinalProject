package com.raveendra.finalproject_binar.presentation.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.FragmentAccountBinding
import com.raveendra.finalproject_binar.databinding.FragmentCourseBinding
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<FragmentAccountBinding>() {

    private val viewModel: AccountViewModel by viewModel()

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAccountBinding
        get() = FragmentAccountBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}