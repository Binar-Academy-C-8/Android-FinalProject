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
import com.raveendra.finalproject_binar.utils.ApiException
import com.raveendra.finalproject_binar.utils.NoInternetException
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtpActivity : BaseViewModelActivity<OtpViewModel, ActivityOtpBinding>() {
    companion object {
        const val EXTRA_EMAIL = "EXTRA_EMAIL"
        const val EXTRA_ID = "EXTRA_ID"
        const val EXTRA_IS_FROM_LOGIN = "EXTRA_IS_FROM_LOGIN"
        fun navigate(context: Context, email: String, id: Int, isFromLogin : Boolean = false) = with(context) {
            startActivity(
                Intent(
                    this,
                    OtpActivity::class.java
                )
                    .putExtra(EXTRA_EMAIL, email)
                    .putExtra(EXTRA_ID, id)
                    .putExtra(EXTRA_IS_FROM_LOGIN, isFromLogin)
            )
        }

        fun navigate(context: Context, email: String,isFromLogin : Boolean = true) = with(context) {
            startActivity(
                Intent(
                    this,
                    OtpActivity::class.java
                )
                    .putExtra(EXTRA_EMAIL, email)
                    .putExtra(EXTRA_IS_FROM_LOGIN, isFromLogin)
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
    private val isFromLoginExtra by lazy {
        intent.getBooleanExtra(EXTRA_IS_FROM_LOGIN, false)
    }

    override val viewModel: OtpViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityOtpBinding
        get() = ActivityOtpBinding::inflate

    override fun setupViews(): Unit = with(binding) {
        tvOtpDesc.text = getString(R.string.label_otp_desc, emailExtra)
        tvResendOtpCountdown.text = getString(R.string.label_otp_timer, "56")
        if (idExtra != 0){
            otpView.apply {
                requestFocus()
                setOtpCompletionListener {
                    viewModel.postVerifyOtp(idExtra, VerifyOtpRequest(code = it))
                }
            }
        }

        tvResendOtp.setOnClickListener {
            viewModel.postRequestOtp(NewOtpRequest(email = emailExtra))
            binding.tvResendOtpCountdown.isInvisible = false
            binding.tvResendOtp.isVisible = false
            startCountdown()
        }

        if (isFromLoginExtra) viewModel.postRequestOtp(NewOtpRequest(emailExtra))
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
                    doOnSuccess = {result ->
                        binding.otpView.apply {
                            requestFocus()
                            setOtpCompletionListener {
                                result.payload?.data?.newOtpRequest?.userId?.let { userId ->
                                    viewModel.postVerifyOtp(
                                        userId, VerifyOtpRequest(code = it))
                                }
                            }
                        }
                    },
                    doOnError = { error ->
                        if(error.exception is ApiException){
                            val exceptionMessage = error.exception.getParsedError()?.message
                            Toast.makeText(
                                this@OtpActivity,
                                exceptionMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }else if (error.exception is NoInternetException){
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
            viewModel.verifyOtpResult.collect {
                it.proceedWhen(
                    doOnSuccess = { result ->
                        preferences.appToken = result.payload?.data?.token.toString()
                        MainActivity.navigateWithFlag(this@OtpActivity)
                    },
                    doOnError = { error ->
                        if(error.exception is ApiException){
                            Toast.makeText(
                                this@OtpActivity,
                                error.exception.getParsedError()?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }else if (error.exception is NoInternetException){
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