package com.raveendra.finalproject_binar.presentation.auth.register

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.databinding.ActivityRegisterBinding
import com.raveendra.finalproject_binar.presentation.auth.otp.OtpActivity
import com.raveendra.finalproject_binar.utils.ApiException
import com.raveendra.finalproject_binar.utils.LabelTextFieldView
import com.raveendra.finalproject_binar.utils.NoInternetException
import com.raveendra.finalproject_binar.utils.ToastyUtil
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.highLightWord
import com.raveendra.finalproject_binar.utils.proceedWhen
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseViewModelActivity<RegisterViewModel, ActivityRegisterBinding>() {
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

    override val viewModel: RegisterViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityRegisterBinding
        get() = ActivityRegisterBinding::inflate

    override fun setupViews() {

        binding.tvNavToLogin.highLightWord(getString(R.string.text_already_have_account_login_highlight)) {
            navigateToLogin()
        }
        binding.btRegister.setOnClickListener {
            doRegister()
        }
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            viewModel.registerResult.collect {
                it.proceedWhen(
                    doOnSuccess = { result ->
                        result.payload?.data?.dataValues?.id?.let { id -> navigateToOtp(id) }
                    },
                    doOnError = { error ->
                        if (error.exception is ApiException) {
                            val exceptionMessage = error.exception.getParsedError()?.message
                            ToastyUtil.configureToasty()
                            Toasty.error(
                                applicationContext,
                                "Register Failed $exceptionMessage",
                                Toast.LENGTH_SHORT,
                                true
                            ).show()
                        } else if (error.exception is NoInternetException) {
                            ToastyUtil.configureToasty()
                            Toasty.error(
                                applicationContext,
                                "Connection Failed Please waiting and try again",
                                Toast.LENGTH_SHORT,
                                true
                            ).show()
                        }
                    }
                )
            }
        }
    }

    private fun navigateToOtp(id: Int) {
        OtpActivity.navigate(this@RegisterActivity, binding.etEmail.getText().toString(), id)
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
            viewModel.postRegister(
                RegisterRequest(
                    email = email,
                    name = fullName,
                    password = password,
                    phoneNumber = phone,
                )
            )
        }
    }

    private fun isFormValid(): Boolean {
        val password = binding.etPassword.getText().toString().trim()
        val fullName = binding.etName.getText().toString().trim()
        val phone = binding.etPhone.getText().toString().trim()
        val email = binding.etEmail.getText().toString().trim()

        return checkNameValidation(fullName) && checkEmailValidation(email) && checkPhoneValidation(
            phone
        ) &&
                checkPasswordValidation(password, binding.etPassword)
    }

    private fun checkNameValidation(fullName: String): Boolean {
        return if (fullName.isEmpty()) {
            binding.etName.setError(true, getString(R.string.text_error_name_empty))
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
            binding.etPhone.setError(true, getString(R.string.text_error_phone_empty))
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