package com.raveendra.finalproject_binar.presentation.auth.otp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityOtpBinding
import com.raveendra.finalproject_binar.presentation.MainActivity
import com.raveendra.finalproject_binar.presentation.auth.login.LoginViewModel
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpActivity : BaseViewModelActivity<LoginViewModel, ActivityOtpBinding>() {
    companion object {
        const val EXTRA_EMAIL = "EXTRA_EMAIL"
        fun navigate(context: Context, email: String) = with(context) {
            startActivity(
                Intent(
                    this,
                    OtpActivity::class.java
                ).putExtra(EXTRA_EMAIL, email)
            )
        }
    }

    private val emailExtra by lazy {
        intent.getStringExtra(EXTRA_EMAIL) ?: "-"
    }

    override val viewModel: LoginViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityOtpBinding
        get() = ActivityOtpBinding::inflate

    override fun setupViews(): Unit = with(binding) {
        tvOtpDesc.text = getString(R.string.label_otp_desc, emailExtra)
        tvResendOtpCountdown.text = getString(R.string.label_otp_timer, "56")
        otpView.apply {
            requestFocus()
            setOtpCompletionListener {
                MainActivity.navigateWithFlag(this@OtpActivity)
            }
        }
        tvResendOtp.setOnClickListener {
            binding.tvResendOtpCountdown.isInvisible = false
            binding.tvResendOtp.isVisible = false
            startCountdown()
        }
    }

    private fun startCountdown() {
        lifecycleScope.launch {
            for (i in 60 downTo 0) {
                updateResendText(i)
                delay(1000)
            }

            binding.tvResendOtpCountdown.isInvisible = true
            binding.tvResendOtp.isVisible = true
        }
    }

    private fun updateResendText(i: Int) {
        runOnUiThread {
            binding.tvResendOtpCountdown.text = getString(R.string.label_otp_timer, i.toString())
        }
    }

    override fun setupObservers() {

    }
}