package com.raveendra.finalproject_binar.presentation.paymenthistory

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.raveendra.finalproject_binar.data.dummy.DummyPaymentDataSourceImpl
import com.raveendra.finalproject_binar.databinding.ActivityPaymentHistoryBinding
import com.raveendra.finalproject_binar.model.Payment
import com.raveendra.finalproject_binar.presentation.paymenthistory.adapter.PaymentAdapter
import com.raveendra.finalproject_binar.presentation.paymenthistory.adapter.PaymentTypeAdadpter
import com.c8.core.utils.base.BaseActivity

class PaymentHistoryActivity: BaseActivity<ActivityPaymentHistoryBinding>() {
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
        PaymentAdapter(PaymentTypeAdadpter.PAID) {payment: Payment ->

        }
    }
    override val bindingInflater: (LayoutInflater) -> ActivityPaymentHistoryBinding
        get() = ActivityPaymentHistoryBinding::inflate

    override fun setupViews() {
        setUpRecyclerView()
        setClickListener()
    }

    private fun setUpRecyclerView(){
        binding.rvList.adapter = adapterPayment
        adapterPayment.setData(DummyPaymentDataSourceImpl().getListPayment())
    }

    private fun setClickListener(){
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

    }
}