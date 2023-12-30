package com.raveendra.finalproject_binar.presentation.payment.payment_summary

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityPaymentSummaryBinding
import com.raveendra.finalproject_binar.presentation.payment.payment_webview.PaymentWebViewActivity
import com.raveendra.finalproject_binar.utils.ApiException
import com.raveendra.finalproject_binar.utils.NoInternetException
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import com.raveendra.finalproject_binar.utils.toIdrCurrency
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
        inclToolbar.tvToolbarTitle.text = getString(R.string.label_payment_summary)
        inclToolbar.ivToolbarBack.setOnClickListener {
            finish()
        }
        btPay.setOnClickListener {
            viewModel.postTransaction(courseIdExtra)
        }

        viewModel.getDetailCourse(courseIdExtra)

    }

    override fun setupObservers() {
        lifecycleScope.launch {
            viewModel.transactionResult.collect {
                it.proceedWhen(
                    doOnSuccess = { result ->
                        PaymentWebViewActivity.navigate(
                            this@PaymentSummaryActivity,
                            result.payload?.createdTransactionData?.linkPayment ?: ""
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
        viewModel.detailData.observe(this) {
            it.proceedWhen(
                doOnSuccess = { success ->
                    val data = success.payload?.data
                    binding.clDetailSummary.isVisible = true
                    binding.apply {
                        ivImg.load(data?.image) {
                            crossfade(true)
                        }
                        tvCategory.text = data?.category
                        tvRating.text = data?.rating.toString()
                        tvTitle.text = data?.courseName
                        tvAuthor.text = data?.courseBy
                        tvLevel.text = data?.courseLevel
                        tvModule.text =
                            getString(R.string.label_var_module, data?.modulePerCourse.toString())
                        tvDuration.text = getString(
                            R.string.label_var_minute,
                            data?.durationPerCourseInMinutes.toString()
                        )

                        tvBasePriceValue.text = data?.coursePrice?.toIdrCurrency()
                        val ppnValue = (data?.coursePrice ?: 0) * 0.11
                        tvPpnValue.text = ppnValue.toIdrCurrency()
                        val totalValue = (data?.coursePrice ?: 0) + ppnValue
                        tvTotalValue.text = totalValue.toIdrCurrency()
                    }
                    binding.shimmerButton.stopShimmer()
                    binding.shimmerButton.isVisible = false
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.btPay.isVisible = true
                    binding.shimmerSum.stopShimmer()
                    binding.shimmerSum.isVisible = false
                },
                doOnLoading = {
                    binding.shimmerSum.startShimmer()
                    binding.shimmerSum.isVisible = true
                    binding.tvSum.isVisible = true
                    binding.shimmerView.startShimmer()
                    binding.shimmerView.isVisible = true
                    binding.shimmerButton.startShimmer()
                    binding.shimmerButton.isVisible = true
                    binding.clDetailSummary.isVisible = false
                    binding.btPay.isVisible = false
                },
                doOnError = { error ->
                    error.message.toString()
                }
            )
        }
    }

}