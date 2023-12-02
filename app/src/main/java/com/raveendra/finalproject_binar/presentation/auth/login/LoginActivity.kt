package com.raveendra.finalproject_binar.presentation.auth.login

import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.LayoutInflater
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityLoginBinding
import com.raveendra.finalproject_binar.presentation.MainActivity
import com.raveendra.finalproject_binar.presentation.auth.register.RegisterActivity
import com.raveendra.finalproject_binar.utils.LabelTextFieldView
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.highLightWord
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseViewModelActivity<LoginViewModel, ActivityLoginBinding>()  {
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

    }

    private fun doLogin() = with(binding) {
        if (isFormValid()) {
            val email = etEmail.getText().toString().trim()
            val password = etPassword.getText().toString().trim()
            MainActivity.navigateWithFlag(this@LoginActivity)
//            viewModel.doLogin(email, password)
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