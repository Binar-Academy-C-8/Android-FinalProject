package com.raveendra.finalproject_binar.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *hrahm,26/11/2023, 05:57
 **/
object GenericViewModelFactory {
    fun create(vm : ViewModel) = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = vm as T
    }
}