package com.raveendra.finalproject_binar.presentation.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import com.raveendra.finalproject_binar.data.dummy.DummyNotificationdataSourceImpl
import com.raveendra.finalproject_binar.databinding.FragmentNotificationsBinding
import com.raveendra.finalproject_binar.model.Notification
import com.raveendra.finalproject_binar.presentation.notifications.adapter.NotificationAdapter
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import com.raveendrag.finalproject_binar.domain.DataDomain
import com.raveendrag.finalproject_binar.domain.UserDomain
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment  : BaseFragment<FragmentNotificationsBinding>() {

    private val viewModel: NotificationsViewModel by viewModel()

    private val adapterNotification: NotificationAdapter by lazy {
        NotificationAdapter() { notification: DataDomain ->

        }
    }
    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotificationsBinding
        get() = FragmentNotificationsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        binding.rvMenuNotification.adapter = adapterNotification
        viewModel.getNotification()
    }

    private fun observeData(){
        viewModel.notification.observe(viewLifecycleOwner){
            it.proceedWhen (
                doOnSuccess = {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.rvMenuNotification.isVisible = true
                    binding.layoutStateMenu.root.isVisible = false
                    it.payload?.let {
                        it.data?.let { it1 -> adapterNotification.setData(it1) }
                    }
                },
                doOnLoading = {
                    binding.rvMenuNotification.isVisible = false
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = true
                    binding.layoutStateMenu.root.isVisible = false
                },
                doOnError = {
                    binding.rvMenuNotification.isVisible = false
                    binding.layoutStateMenu.tvError.error
                    it.exception?.message.orEmpty()
                }
            )
        }
    }
}