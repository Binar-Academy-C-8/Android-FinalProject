package com.raveendra.finalproject_binar.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.raveendra.finalproject_binar.databinding.FragmentAccountBinding
import com.raveendra.finalproject_binar.databinding.FragmentHomeBinding
import com.raveendra.finalproject_binar.presentation.account.AccountViewModel
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment  : BaseFragment<FragmentAccountBinding>() {

    private val viewModel: HomeViewModel by viewModel()

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAccountBinding
        get() = FragmentAccountBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}