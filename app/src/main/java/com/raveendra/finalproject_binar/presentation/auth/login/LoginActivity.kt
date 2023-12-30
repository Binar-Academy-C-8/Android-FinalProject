package com.raveendra.finalproject_binar.presentation.auth.login

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.local.sharedpref.PreferenceManager
import com.raveendra.finalproject_binar.data.request.LoginRequest
import com.raveendra.finalproject_binar.databinding.ActivityLoginBinding
import com.raveendra.finalproject_binar.presentation.MainActivity
import com.raveendra.finalproject_binar.presentation.auth.forgotpassword.ForgotPasswordActivity
import com.raveendra.finalproject_binar.presentation.auth.otp.OtpActivity
import com.raveendra.finalproject_binar.presentation.auth.register.RegisterActivity
import com.raveendra.finalproject_binar.utils.ApiException
import com.raveendra.finalproject_binar.utils.LabelTextFieldView
import com.raveendra.finalproject_binar.utils.NoInternetException
import com.raveendra.finalproject_binar.utils.ToastyUtil
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.highLightWord
import com.raveendra.finalproject_binar.utils.proceedWhen
import es.dmoral.toasty.Toasty
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
        tvForgetPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }
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
                        preferences.appToken = result.payload?.data?.token.toString()
                        MainActivity.navigateWithFlag(this@LoginActivity)
                        binding.lottie.speed = 6f
                        binding.lottie.playAnimation()
                        binding.lottie.isVisible = false
                        binding.btLogin.isVisible = false
                        ToastyUtil.configureToasty()
                        Toasty.success(
                            applicationContext,
                            "Login Berhasil, Selamat Datang!",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    }, doOnLoading = {
                        binding.lottie.speed = 6f
                        binding.lottie.playAnimation()
                        binding.lottie.isVisible = true
                        binding.btLogin.isVisible = false
                    },
                    doOnError = { error ->
                        binding.lottie.speed = 6f
                        binding.lottie.playAnimation()
                        binding.btLogin.isVisible = true
                        binding.lottie.isVisible = false
                        if (error.exception is ApiException) {
                            val exceptionMessage = error.exception.getParsedError()?.message
                            if (exceptionMessage.equals(
                                    "Pengguna belum diverifikasi",
                                    ignoreCase = true
                                )
                            ) {
                                OtpActivity.navigate(
                                    this@LoginActivity,
                                    binding.etEmail.getText().toString().trim(),
                                    2
                                )
                            } else {
                                ToastyUtil.configureToasty()
                                Toasty.error(
                                    applicationContext,
                                    "Login Failed Please check username and password",
                                    Toast.LENGTH_SHORT,
                                    true
                                ).show()
                            }
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