package com.raveendra.finalproject_binar.presentation.payment.payment_summary

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityPaymentSummaryBinding
import com.raveendra.finalproject_binar.presentation.payment.payment_webview.PaymentWebViewActivity
import com.raveendra.finalproject_binar.utils.ApiException
import com.raveendra.finalproject_binar.utils.NoInternetException
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentSummaryActivity :
    BaseViewModelActivity<PaymentSummaryViewModel, ActivityPaymentSummaryBinding>() {
    companion object {
        const val EXTRA_COURSE_ID = "EXTRA_ID"
        fun navigate(context: Context, courseId: Int) = with(context) {
            startActivity(
                Intent(
                    this,
                    PaymentSummaryActivity::class.java
                ).putExtra(EXTRA_COURSE_ID, courseId)
            )
        }
    }

    private val courseIdExtra by lazy {
        intent.getIntExtra(EXTRA_COURSE_ID, 0)
    }

    override val viewModel: PaymentSummaryViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityPaymentSummaryBinding
        get() = ActivityPaymentSummaryBinding::inflate

    override fun setupViews() = with(binding) {
        btPay.setOnClickListener {
            viewModel.postTransaction(courseIdExtra)
        }

    }

    override fun setupObservers() {
        lifecycleScope.launch {
            viewModel.transactionResult.collect {
                it.proceedWhen(
                    doOnSuccess = { result ->
                        PaymentWebViewActivity.navigate(
                            this@PaymentSummaryActivity,
                            result.payload?.url ?: ""
                        )
                    },
                    doOnError = { error ->
                        if (error.exception is ApiException) {
                            val exceptionMessage = error.exception.getParsedError()?.message
                            Toast.makeText(
                                this@PaymentSummaryActivity,
                                exceptionMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (error.exception is NoInternetException) {
                            Toast.makeText(
                                this@PaymentSummaryActivity,
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