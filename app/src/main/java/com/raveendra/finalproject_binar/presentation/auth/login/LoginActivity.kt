package com.raveendra.finalproject_binar.presentation.auth.login

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.local.sharedpref.PreferenceManager
import com.raveendra.finalproject_binar.databinding.ActivityLoginBinding
import com.raveendra.finalproject_binar.presentation.MainActivity
import com.raveendra.finalproject_binar.presentation.auth.register.RegisterActivity
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.highLightWord
import com.raveendra.finalproject_binar.utils.proceedWhen
import com.raveendra.finalproject_binar.data.request.LoginRequest
import com.raveendra.finalproject_binar.presentation.auth.otp.OtpActivity
import com.raveendra.finalproject_binar.utils.ApiException
import com.raveendra.finalproject_binar.utils.LabelTextFieldView
import com.raveendra.finalproject_binar.utils.NoInternetException
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseViewModelActivity<LoginViewModel, ActivityLoginBinding>() {
    companion object {
        fun navigate(context: Context) = with(context) {
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
            )
        }
    }

    override val viewModel: LoginViewModel by viewModel()

    private val preferences: PreferenceManager by inject()

    override val bindingInflater: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate

    override fun setupViews() = with(binding) {
        btLogin.setOnClickListener {
            doLogin()
        }
        tvNavToRegister.highLightWord(getString(R.string.text_dont_have_account_register_highlight)) {
            RegisterActivity.navigate(this@LoginActivity)
        }
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            viewModel.loginResult.collect {
                it.proceedWhen(
                    doOnSuccess = { result ->
                        preferences.appToken = result.payload?.token.toString()
                        MainActivity.navigateWithFlag(this@LoginActivity)
                    },
                    doOnError = {error ->
                        if(error.exception is ApiException){
                            val exceptionMessage = error.exception.getParsedError()?.message
                            if (exceptionMessage.equals("User not verified", ignoreCase = true)) {
                                OtpActivity.navigate(
                                    this@LoginActivity,
                                    binding.etEmail.getText().toString().trim()
                                )
                            } else {
                                Log.d("test22", error.exception.getParsedError()?.message.toString())
                                Toast.makeText(
                                    this@LoginActivity,
                                    exceptionMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }else if (error.exception is NoInternetException){
                            Toast.makeText(
                                this@LoginActivity,
                                getString(R.string.label_error_no_internet),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            }
        }
    }

    private fun doLogin() = with(binding) {
        if (isFormValid()) {
            val email = etEmail.getText().toString().trim()
            val password = etPassword.getText().toString().trim()

            viewModel.postLogin(LoginRequest(email = email, password = password))
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.etEmail.getText().toString().trim()
        val password = binding.etPassword.getText().toString().trim()

        return checkEmailValidation(email) &&
                checkPasswordValidation(password, binding.etPassword)
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