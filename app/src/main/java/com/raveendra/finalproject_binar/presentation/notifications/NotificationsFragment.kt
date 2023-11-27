package com.raveendra.finalproject_binar.presentation.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.raveendra.finalproject_binar.data.dummy.DummyNotificationdataSourceImpl
import com.raveendra.finalproject_binar.databinding.FragmentAccountBinding
import com.raveendra.finalproject_binar.databinding.FragmentNotificationsBinding
import com.raveendra.finalproject_binar.model.Notification
import com.raveendra.finalproject_binar.presentation.account.AccountViewModel
import com.raveendra.finalproject_binar.presentation.notifications.adapter.NotificationAdapter
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment  : BaseFragment<FragmentNotificationsBinding>() {

    private val viewModel: NotificationsViewModel by viewModel()
    private val adapterProduct: NotificationAdapter by lazy {
        NotificationAdapter() { notification: Notification   ->

        }
    }
    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotificationsBinding
        get() = FragmentNotificationsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvMenuNotification.adapter = adapterProduct
        adapterProduct.setData(DummyNotificationdataSourceImpl().getNotificationData())
    }
}