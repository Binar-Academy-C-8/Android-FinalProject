package com.raveendra.finalproject_binar.presentation.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.FragmentNotificationsBinding
import com.raveendra.finalproject_binar.presentation.notifications.adapter.NotificationAdapter
import com.raveendra.finalproject_binar.utils.ApiException
import com.raveendra.finalproject_binar.utils.NoInternetException
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import com.raveendrag.finalproject_binar.domain.DataDomain
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment  : BaseFragment<FragmentNotificationsBinding>() {

    private val viewModel: NotificationViewModel by viewModel()

    private val adapterNotification: NotificationAdapter by lazy {
        NotificationAdapter { notification: DataDomain ->

        }
    }

    private val swipeResfreshListener = SwipeRefreshList{
        viewModel.getNotification()
    }
    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotificationsBinding
        get() = FragmentNotificationsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
        getNotification()
        setupSwipeRefreshLayout()
    }

    private fun setupRecyclerView() {
        binding.rvMenuNotification.adapter = adapterNotification
        viewModel.getNotification()
    }

    private fun observeData(){
        viewModel.notification.observe(viewLifecycleOwner){
            it.proceedWhen (
                doOnSuccess = {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.clNotLogin.isVisible = false
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
                    binding.clNotLogin.isVisible = false
                    binding.layoutStateMenu.root.isVisible = false
                },
                doOnError = { error ->
                    if (error.exception is ApiException) {
                        if (error.exception.httpCode == 500) {
                            binding.swipeRefreshLayout.isVisible = false
                            binding.clNotLogin.isVisible = true
                        } else {
                            val exceptionMessage = error.exception.getParsedError()?.message
                            if (!exceptionMessage.isNullOrBlank()) {
                                Toast.makeText(
                                    requireContext(),
                                    exceptionMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    } else if (error.exception is NoInternetException) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.label_error_no_internet),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.rvMenuNotification.isVisible = false
                }
            )
        }
    }

    private fun getNotification() {
        viewModel.getNotification()
    }
    private fun setupSwipeRefreshLayout() {
        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(swipeResfreshListener)
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.primary_dark_blue_06)
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotification()
    }
}