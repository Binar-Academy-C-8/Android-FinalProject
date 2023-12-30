package com.raveendra.finalproject_binar.presentation.account.change_password

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.request.ChangePasswordRequest
import com.raveendra.finalproject_binar.databinding.ActivityChangePasswordBinding
import com.raveendra.finalproject_binar.presentation.auth.otp.OtpActivity
import com.raveendra.finalproject_binar.utils.LabelTextFieldView
import com.raveendra.finalproject_binar.utils.ToastyUtil
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordActivity : BaseViewModelActivity<ChangePasswordViewModel, ActivityChangePasswordBinding>() {

    override val viewModel: ChangePasswordViewModel by viewModel()

    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        fun navigate(context: Context, id: Int) = with(context) {
            startActivity(
                Intent(
                    this,
                    ChangePasswordActivity::class.java
                ).putExtra(EXTRA_ID, id)
            )
        }
    }


    private val idExtra by lazy {
        intent.getIntExtra(EXTRA_ID, 0)
    }

    override val bindingInflater: (LayoutInflater) -> ActivityChangePasswordBinding
        get() = ActivityChangePasswordBinding::inflate

    override fun setupViews() {
        binding.ivBack.setOnClickListener{
            onBackPressed()
        }
        binding.btnFinishChangePw.setOnClickListener {
            changePassword()
        }

    }

    private fun changePassword() {
        if (isFormValid()) {
            val oldPassword = binding.etOldPassword.getText().toString().trim()
            val newPassword = binding.etNewPassword.getText().toString().trim()
            val confirmPassword = binding.etRepeatNewPassword.getText().toString().trim()
            viewModel.changePassword(idExtra, ChangePasswordRequest(oldPassword, newPassword, confirmPassword))
        }
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            viewModel.changePassword.collect {
                it.proceedWhen(
                    doOnSuccess = {
                        Toast.makeText(this@ChangePasswordActivity, "Ubah Password Berhasil!", Toast.LENGTH_SHORT).show()
                        finish()
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

    private fun isFormValid(): Boolean {
        val oldPassword = binding.etOldPassword.getText().toString().trim()
        val newPassword = binding.etNewPassword.getText().toString().trim()
        val confirmPassword = binding.etRepeatNewPassword.getText().toString().trim()

        return checkOldPasswordValidation(oldPassword, binding.etOldPassword)
                && checkNewPasswordValidation(newPassword,oldPassword, binding.etNewPassword)
                && checkRepeatPasswordValidation(newPassword, confirmPassword)
    }

    private fun checkOldPasswordValidation(
        oldPassword: String,
        textInputLayout: LabelTextFieldView
    ): Boolean {
        return if (oldPassword.isEmpty()) {
            textInputLayout.setError(true, getString(R.string.text_error_password_empty))
            false
        } else if (oldPassword.length < 8) {
            textInputLayout.setError(true, getString(R.string.text_error_password_less_than_8_char))
            false
        } else {
            textInputLayout.setError(false)
            true
        }
    }


    private fun checkNewPasswordValidation(
        newPassword: String,
        oldPassword: String,
        textInputLayout: LabelTextFieldView
    ): Boolean {
        return if (newPassword.isEmpty()) {
            textInputLayout.setError(true, getString(R.string.text_error_password_empty))
            false
        } else if (newPassword.length < 8) {
            textInputLayout.setError(true, getString(R.string.text_error_password_less_than_8_char))
            false
        } else if (oldPassword == newPassword) {
            textInputLayout.setError(true, "Password baru tidak boleh sama dengan password lama")
            false
        } else {
            textInputLayout.setError(false)
            true
        }
    }

    private fun checkRepeatPasswordValidation(
        newPassword: String,
        repeatPassword: String,
    ): Boolean {
        return if (newPassword == repeatPassword) {
            true
        } else {
            binding.etRepeatNewPassword.setError(true, "password baru tidak cocok")
            false
        }
    }

}