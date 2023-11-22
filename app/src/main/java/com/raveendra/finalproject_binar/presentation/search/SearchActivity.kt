package com.raveendra.finalproject_binar.presentation.search

import android.view.LayoutInflater
import com.raveendra.finalproject_binar.databinding.ActivitySearchBinding
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity  : BaseViewModelActivity<SearchViewModel, ActivitySearchBinding>() {
    override val viewModel: SearchViewModel by viewModel()



    override val bindingInflater: (LayoutInflater) -> ActivitySearchBinding
        get() = ActivitySearchBinding::inflate

    override fun setupViews() {
        // ini buat setup view kaya di oncreate biasa, jadi gaperlu oncreate lagi udah pake base soalnya
    }

    override fun setupObservers() {
        // ini buat observe data dari viewmodel
    }
}