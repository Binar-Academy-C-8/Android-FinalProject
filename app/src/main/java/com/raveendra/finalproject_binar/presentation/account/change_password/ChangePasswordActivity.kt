package com.raveendra.finalproject_binar.presentation.account.change_password

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.raveendra.finalproject_binar.databinding.ActivityChangePasswordBinding
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity

class ChangePasswordActivity : BaseViewModelActivity<ChangePasswordViewModel, ActivityChangePasswordBinding>() {

    override val viewModel: ChangePasswordViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ActivityChangePasswordBinding
        get() = ActivityChangePasswordBinding::inflate

    override fun setupViews() {

    }

    override fun setupObservers() {

    }

}