package com.raveendra.finalproject_binar.presentation.auth.resetpassword

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.request.ResetPasswordRequest
import com.raveendra.finalproject_binar.databinding.ActivityResetPasswordBinding
import com.raveendra.finalproject_binar.presentation.MainActivity
import com.raveendra.finalproject_binar.presentation.auth.otp.OtpActivity
import com.raveendra.finalproject_binar.utils.LabelTextFieldView
import com.raveendra.finalproject_binar.utils.ToastyUtil
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordActivity :
    BaseViewModelActivity<ResetPasswordViewModel, ActivityResetPasswordBinding>() {
    override val viewModel: ResetPasswordViewModel by viewModel()

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        fun navigate(context: Context, id: Int) =
            with(context) {
                startActivity(
                    Intent(
                        this,
                        ResetPasswordActivity::class.java
                    )
                        .putExtra(EXTRA_ID, id)
                )
            }
    }

    private val idExtra by lazy {
        intent.getIntExtra(OtpActivity.EXTRA_ID, 0)
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            viewModel.resetPassword.collect {
                it.proceedWhen(
                    doOnSuccess = {
                        MainActivity.navigateWithFlag(this@ResetPasswordActivity)
                        ToastyUtil.configureToasty()
                        Toasty.success(
                            applicationContext,
                            "Berhasil melakukan reset password!",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    }, doOnError = {
                        ToastyUtil.configureToasty()
                        Toasty.error(
                            applicationContext,
                            "${it.payload?.message}",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    }
                )
            }
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityResetPasswordBinding
        get() = ActivityResetPasswordBinding::inflate

    override fun setupViews() {
        binding.btResetPassword.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() = with(binding) {
        if (isFormValid()) {
            val password = etPassword.getText().toString().trim()
            viewModel.resetPassword(idExtra, ResetPasswordRequest(password = password))
        }

    }

    private fun isFormValid(): Boolean {
        val password = binding.etPassword.getText().toString().trim()
        val passwordConfirmation = binding.etPasswordConfirm.getText().toString().trim()

        return checkPasswordValidation(password, binding.etPassword) && checkPasswordValidation(
            passwordConfirmation,
            binding.etPasswordConfirm
        ) && checkPasswordEquality(password, passwordConfirmation)
    }

    private fun checkPasswordEquality(password: String, passwordConfirmation: String): Boolean {
        return if (password == passwordConfirmation) {
            true
        } else {
            binding.etPasswordConfirm.setError(true, "Kata sandi tidak cocok")
            false
        }
    }

    private fun checkPasswordValidation(
        confirmPassword: String,
        textInputLayout: LabelTextFieldView
    ): Boolean {
        return if (confirmPassword.isEmpty()) {
            textInputLayout.setError(true, getString(R.string.text_error_password_empty))
            false
        } else if (confirmPassword.length < 8) {
            textInputLayout.setError(true, getString(R.string.text_error_password_less_than_8_char))

            false
        } else {
            textInputLayout.setError(false)
            true
        }
    }
}