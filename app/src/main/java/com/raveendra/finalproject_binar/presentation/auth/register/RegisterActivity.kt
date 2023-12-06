package com.raveendra.finalproject_binar.presentation.auth.register

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.LayoutInflater
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityRegisterBinding
import com.raveendra.finalproject_binar.presentation.auth.login.LoginViewModel
import com.raveendra.finalproject_binar.presentation.auth.otp.OtpActivity
import com.c8.core.utils.base.BaseViewModelActivity
import com.c8.core.utils.highLightWord
import com.raveendra.finalproject_binar.utils.LabelTextFieldView
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseViewModelActivity<LoginViewModel, ActivityRegisterBinding>()  {
    companion object {
        fun navigate(context: Context) = with(context) {
            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }
    }
    override val viewModel: LoginViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityRegisterBinding
        get() = ActivityRegisterBinding::inflate

    override fun setupViews() {

        binding.tvNavToLogin.highLightWord(getString(R.string.text_already_have_account_login_highlight)) {
            navigateToLogin()
        }
        binding.btRegister.setOnClickListener {
            doRegister()
//            viewModel.doRegister(
//                binding.etName.text.toString(),
//                binding.etEmail.text.toString(),
//                binding.etEmail.text.toString(),
//                binding.etPassword.text.toString()
//            )
        }
    }

    override fun setupObservers() {

    }

    private fun navigateToOtp() {
        OtpActivity.navigate(this@RegisterActivity, binding.etEmail.getText().toString())
    }

    private fun navigateToLogin() {
        finish()
    }

    private fun doRegister() {
        if (isFormValid()) {
            val email = binding.etEmail.getText().toString().trim()
            val password = binding.etPassword.getText().toString().trim()
            val fullName = binding.etName.getText().toString().trim()
            val phone = binding.etPhone.getText().toString().trim()
//            viewModel.doRegister(fullName, email,phone, password)
            navigateToOtp()
        }
    }

    private fun isFormValid(): Boolean {
        val password = binding.etPassword.getText().toString().trim()
        val fullName = binding.etName.getText().toString().trim()
        val phone = binding.etPhone.getText().toString().trim()
        val email = binding.etEmail.getText().toString().trim()

        return checkNameValidation(fullName) && checkEmailValidation(email) && checkPhoneValidation(phone) &&
                checkPasswordValidation(password, binding.etPassword)
    }

    private fun checkNameValidation(fullName: String): Boolean {
        return if (fullName.isEmpty()) {
            binding.etName.setError(true,getString(R.string.text_error_name_empty))
            false
        } else {
            binding.etName.setError(false)
            true
        }
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
    private fun checkPhoneValidation(phone: String): Boolean {
        return if (phone.isEmpty()) {
            binding.etPhone.setError(true,getString(R.string.text_error_phone_empty))
            false
        } else {
            binding.etPhone.setError(false)
            true
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