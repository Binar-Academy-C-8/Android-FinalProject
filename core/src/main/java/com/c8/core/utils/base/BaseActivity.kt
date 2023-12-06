package com.c8.core.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<BIND : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: BIND

    abstract val bindingInflater: (LayoutInflater) -> BIND

    protected abstract fun setupViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(LayoutInflater.from(this))
        setContentView(binding.root)
        setupViews()
    }
}
