package com.raveendra.finalproject_binar.presentation.auth.forgotpassword

import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.request.ForgotPasswordRequest
import com.raveendra.finalproject_binar.databinding.ActivityForgotPasswordBinding
import com.raveendra.finalproject_binar.presentation.auth.otp.OtpActivity
import com.raveendra.finalproject_binar.utils.ToastyUtil
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgotPasswordActivity :
    BaseViewModelActivity<ForgotPasswordViewModel, ActivityForgotPasswordBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityForgotPasswordBinding
        get() = ActivityForgotPasswordBinding::inflate
    override val viewModel: ForgotPasswordViewModel by viewModel()
    override fun setupObservers() {
        lifecycleScope.launch {
            viewModel.forgotPassword.collect {
                it.proceedWhen(
                    doOnSuccess = {
                        it.payload?.data?.newOtpRequest?.userId?.let { it1 ->
                            OtpActivity.navigate(
                                this@ForgotPasswordActivity,
                                binding.etEmail.getText().toString().trim(),
                                it1,
                                3
                            )
                        }
                        binding.lottie.speed = 6f
                        binding.lottie.playAnimation()
                        binding.lottie.isVisible = false
                        binding.btLogin.isVisible = false
                        ToastyUtil.configureToasty()
                        Toasty.success(
                            applicationContext,
                            "Berhasil mengirimkan kode verifikasi!",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    }, doOnLoading = {
                        binding.lottie.speed = 6f
                        binding.lottie.playAnimation()
                        binding.lottie.isVisible = true
                        binding.btLogin.isVisible = false
                    },
                    doOnError = {
                        binding.lottie.speed = 6f
                        binding.lottie.playAnimation()
                        binding.btLogin.isVisible = true
                        binding.lottie.isVisible = false
                        ToastyUtil.configureToasty()
                        Toasty.error(
                            applicationContext,
                            "Email tidak terdaftar!",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    }
                )
            }
        }
    }

    override fun setupViews() {
        binding.btLogin.setOnClickListener {
            doOtpSend()
        }
    }

    private fun doOtpSend() = with(binding) {
        if (isFormValid()) {
            val email = etEmail.getText().toString().trim()
            viewModel.forgotPassword(ForgotPasswordRequest(email = email))
        }

    }

    private fun isFormValid(): Boolean {
        val email = binding.etEmail.getText().toString().trim()
        return checkEmailValidation(email)
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.etEmail.setError(true, getString(R.string.text_error_email_empty))
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError(true, getString(R.string.text_error_email_invalid))
            false
        } else {
            binding.etEmail.setError(false)
            true
        }
    }


}