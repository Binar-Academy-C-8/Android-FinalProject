package com.raveendra.finalproject_binar.presentation.account.payment_history

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.raveendra.finalproject_binar.data.dummy.DummyPaymentDataSourceImpl
import com.raveendra.finalproject_binar.databinding.ActivityPaymentHistoryBinding
import com.raveendra.finalproject_binar.domain.CoursePaymentDomain
import com.raveendra.finalproject_binar.domain.UserTransactionDomain
import com.raveendra.finalproject_binar.model.Payment
import com.raveendra.finalproject_binar.presentation.account.payment_history.adapter.PaymentAdapter
import com.raveendra.finalproject_binar.presentation.account.payment_history.adapter.PaymentTypeAdadpter
import com.raveendra.finalproject_binar.utils.base.BaseActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentHistoryActivity: BaseActivity<ActivityPaymentHistoryBinding>() {

    private val viewModel : PaymentHistoryViewModel by viewModel()

    companion object {
        fun navigate(context: Context) = with(context) {
            startActivity(
                Intent(
                    this,
                    PaymentHistoryActivity::class.java
                )
            )
        }
    }

    private val adapterPayment : PaymentAdapter by lazy {
        PaymentAdapter{ payment: UserTransactionDomain ->

        }
    }
    override val bindingInflater: (LayoutInflater) -> ActivityPaymentHistoryBinding
        get() = ActivityPaymentHistoryBinding::inflate

    override fun setupViews() {
        setUpRecyclerView()
        setClickListener()
        observeData()

    }

    private fun setUpRecyclerView(){
        binding.rvList.adapter = adapterPayment
        viewModel.getHistoryPayment()
    }

    private fun setClickListener(){
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

    }
    private  fun observeData(){
        viewModel.historyPayment.observe(this){
            it.proceedWhen (
                doOnSuccess = {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.rvList.isVisible = true
                    binding.layoutStateCategory.tvError.isVisible = false
                    it.payload?.let {
                        it.userTransactions?.let { it1 -> adapterPayment.setData(it1) }
                    }
                },
                doOnLoading = {
                    binding.rvList.isVisible = false
                    binding.shimmerView.startShimmer()
                    binding.shimmerView.isVisible = true
                    binding.layoutStateCategory.root.isVisible = false
                },
                doOnError = {
                    binding.rvList.isVisible = false
                    binding.layoutStateCategory.tvError.error
                    it.exception?.message.orEmpty()
                }
            )
        }

    }
}