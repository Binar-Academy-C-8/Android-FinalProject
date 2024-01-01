package com.raveendra.finalproject_binar.presentation.auth.otp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.local.sharedpref.PreferenceManager
import com.raveendra.finalproject_binar.data.request.NewOtpRequest
import com.raveendra.finalproject_binar.data.request.VerifyOtpRequest
import com.raveendra.finalproject_binar.databinding.ActivityOtpBinding
import com.raveendra.finalproject_binar.presentation.MainActivity
import com.raveendra.finalproject_binar.presentation.account.profile.ProfileActivity
import com.raveendra.finalproject_binar.presentation.auth.resetpassword.ResetPasswordActivity
import com.raveendra.finalproject_binar.utils.ApiException
import com.raveendra.finalproject_binar.utils.NoInternetException
import com.raveendra.finalproject_binar.utils.ToastyUtil
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpActivity : BaseViewModelActivity<OtpViewModel, ActivityOtpBinding>() {
    companion object {
        const val EXTRA_EMAIL = "EXTRA_EMAIL"
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_ORIGIN = "EXTRA_ORIGIN"
        fun navigate(context: Context, email: String, id: Int, origin: Int = 0) =
            with(context) {
                startActivity(
                    Intent(
                        this,
                        OtpActivity::class.java
                    )
                        .putExtra(EXTRA_EMAIL, email)
                        .putExtra(EXTRA_ID, id)
                        .putExtra(EXTRA_ORIGIN, origin)
                )
            }

        fun navigate(context: Context, email: String, origin: Int = 0) = with(context) {
            startActivity(
                Intent(
                    this,
                    OtpActivity::class.java
                )
                    .putExtra(EXTRA_EMAIL, email)
                    .putExtra(EXTRA_ORIGIN, origin)
            )
        }
    }

    private val preferences: PreferenceManager by inject()

    private val emailExtra by lazy {
        intent.getStringExtra(EXTRA_EMAIL) ?: "-"
    }
    private val idExtra by lazy {
        intent.getIntExtra(EXTRA_ID, 0)
    }
    private val originExtra by lazy {
        intent.getIntExtra(EXTRA_ORIGIN, 0)
    }

    override val viewModel: OtpViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityOtpBinding
        get() = ActivityOtpBinding::inflate

    override fun setupViews(): Unit = with(binding) {
        tvOtpDesc.text = getString(R.string.label_otp_desc, emailExtra)
        tvResendOtpCountdown.text = getString(R.string.label_otp_timer, "56")
        if (originExtra == 1) {
            otpView.apply {
                requestFocus()
                setOtpCompletionListener {
                    viewModel.postVerifyOtp(idExtra, VerifyOtpRequest(code = it))
                }
            }
        }
        if (originExtra == 3) {
            otpView.apply {
                requestFocus()
                setOtpCompletionListener {
                    viewModel.forgotPassword(idExtra, VerifyOtpRequest(code = it))
                }
            }
        }

        tvResendOtp.setOnClickListener {
            viewModel.postRequestOtp(NewOtpRequest(email = emailExtra))
            binding.tvResendOtpCountdown.isInvisible = false
            binding.tvResendOtp.isVisible = false
            startCountdown()
        }

        if (originExtra == 2) viewModel.postRequestOtp(NewOtpRequest(emailExtra))
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
        lifecycleScope.launch {
            viewModel.requestOtpResult.collect {
                it.proceedWhen(
                    doOnSuccess = { result ->
                        binding.otpView.apply {
                            requestFocus()
                            setOtpCompletionListener {
                                result.payload?.data?.newOtpRequest?.userId?.let { userId ->
                                    viewModel.postVerifyOtp(
                                        userId, VerifyOtpRequest(code = it)
                                    )
                                }
                            }
                        }
                    },
                    doOnError = { error ->
                        if (error.exception is ApiException) {
                            val exceptionMessage = error.exception.getParsedError()?.message
                            ToastyUtil.configureToasty()
                            Toasty.error(
                                applicationContext,
                                "OTP Not Verified $exceptionMessage",
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
        lifecycleScope.launch {
            viewModel.verifyOtpResult.collect {
                it.proceedWhen(
                    doOnSuccess = { result ->
                        binding.pbLoading.isVisible = false
                        preferences.appToken = result.payload?.data?.token.toString()
                        MainActivity.navigateWithFlag(this@OtpActivity)

                    },
                    doOnLoading = {
                        binding.pbLoading.isVisible = true
                    },
                    doOnError = { error ->
                        binding.pbLoading.isVisible = false
                        if (error.exception is ApiException) {
                            Toast.makeText(
                                this@OtpActivity,
                                error.exception.getParsedError()?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (error.exception is NoInternetException) {
                            Toast.makeText(
                                this@OtpActivity,
                                getString(R.string.label_error_no_internet),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            }
        }
        lifecycleScope.launch {
            viewModel.forgotPasswordUserId.collect {
                it.proceedWhen(
                    doOnSuccess = { result ->
                        binding.pbLoading.isVisible = false
                        val message = result.payload?.message
                        ResetPasswordActivity.navigate(this@OtpActivity, idExtra)
                        Toast.makeText(this@OtpActivity, "$message", Toast.LENGTH_SHORT).show()
                    },
                    doOnLoading = {
                        binding.pbLoading.isVisible = true
                    },
                    doOnError = { error ->
                        binding.pbLoading.isVisible = false
                        if (error.exception is ApiException) {
                            Toast.makeText(
                                this@OtpActivity,
                                error.exception.getParsedError()?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (error.exception is NoInternetException) {
                            Toast.makeText(
                                this@OtpActivity,
                                getString(R.string.label_error_no_internet),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            }
        }
    }
}