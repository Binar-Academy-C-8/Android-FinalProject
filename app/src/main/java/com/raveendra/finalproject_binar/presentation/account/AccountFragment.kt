package com.raveendra.finalproject_binar.presentation.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raveendra.finalproject_binar.databinding.FragmentAccountBinding
import com.raveendra.finalproject_binar.presentation.auth.login.LoginActivity
import com.raveendra.finalproject_binar.presentation.paymenthistory.PaymentHistoryActivity
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<FragmentAccountBinding>() {

    private val viewModel: AccountViewModel by viewModel()

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAccountBinding
        get() = FragmentAccountBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() = with(binding) {
        llPaymentHistory.setOnClickListener {
            PaymentHistoryActivity.navigate(requireContext())
        }

        llLogOut.setOnClickListener {
            LoginActivity.navigate(requireContext())
        }
    }
}