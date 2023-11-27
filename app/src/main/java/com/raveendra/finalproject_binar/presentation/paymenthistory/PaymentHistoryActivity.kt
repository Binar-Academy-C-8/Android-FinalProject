package com.raveendra.finalproject_binar.presentation.paymenthistory

import android.view.LayoutInflater
import android.view.View
import com.raveendra.finalproject_binar.data.dummy.DummyPaymentDataSource
import com.raveendra.finalproject_binar.data.dummy.DummyPaymentDataSourceImpl
import com.raveendra.finalproject_binar.databinding.ActivityPaymentHistoryBinding
import com.raveendra.finalproject_binar.model.Payment
import com.raveendra.finalproject_binar.presentation.paymenthistory.adapter.PaymentAdapter
import com.raveendra.finalproject_binar.presentation.paymenthistory.adapter.PaymentTypeAdadpter
import com.raveendra.finalproject_binar.utils.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentHistoryActivity: BaseActivity<ActivityPaymentHistoryBinding>() {
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