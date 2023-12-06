package com.c8.core.utils.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseViewModelActivity<VM : ViewModel, BIND : ViewBinding> : BaseActivity<BIND>() {
    abstract val viewModel: VM
    protected abstract fun setupObservers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }
}
