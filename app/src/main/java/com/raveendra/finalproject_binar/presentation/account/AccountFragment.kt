package com.raveendra.finalproject_binar.presentation.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.raveendra.finalproject_binar.BuildConfig
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.data.local.sharedpref.PreferenceManager
import com.raveendra.finalproject_binar.databinding.FragmentAccountBinding
import com.raveendra.finalproject_binar.presentation.account.change_password.ChangePasswordActivity
import com.raveendra.finalproject_binar.presentation.account.payment_history.PaymentHistoryActivity
import com.raveendra.finalproject_binar.presentation.account.profile.ProfileActivity
import com.raveendra.finalproject_binar.presentation.auth.login.LoginActivity
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<FragmentAccountBinding>() {

    private val viewModel: AccountViewModel by viewModel()

    private val preferences: PreferenceManager by inject()


    private val swipeRefreshListener = SwipeRefreshList {
        viewModel.getProfile()
    }

    override val inflateLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAccountBinding
        get() = FragmentAccountBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        setupViews()
        setupSwipeRefreshLayout()
    }


    @SuppressLint("SetTextI18n")
    private fun setupViews() = with(binding) {
        llPaymentHistory.setOnClickListener {
            PaymentHistoryActivity.navigate(requireContext())
        }


        llLogOut.setOnClickListener {
            preferences.clear()
            LoginActivity.navigate(requireContext())
        }
        tvVersion.text = "${BuildConfig.FLAVOR} (${BuildConfig.VERSION_NAME})"

        viewModel.getProfile()
        btLogin.setOnClickListener {
            LoginActivity.navigate(requireContext())
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.resultProfile.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = { result ->
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                    binding.llMyProfile.setOnClickListener {
                        result.payload?.data?.id.let {
                            it?.let { it1 -> ProfileActivity.navigate(requireContext(), it1) }
                        }
                    }
                    binding.ivProfile.load(result.payload?.data?.image) {
                        placeholder(R.color.primary_dark_blue_06)
                        transformations(
                            CircleCropTransformation()
                        )
                        crossfade(true)
                    }
                    binding.changePassword.setOnClickListener {
                        result.payload?.data?.id?.let { it1 ->
                            ChangePasswordActivity.navigate(
                                requireContext(),
                                it1
                            )
                        }
                    }
                    binding.tvName.text = result.payload?.data?.name ?: "-"
                    binding.tvEmail.text = result.payload?.data?.email ?: "-"
                    binding.swipeRefreshLayout.isRefreshing = false
                },
                doOnLoading = {
                    binding.shimmerView.startShimmer()
                    binding.shimmerView.isVisible = true
                },
                doOnError = {
                    binding.shimmerView.stopShimmer()
                    binding.shimmerView.isVisible = false
                })
        }


    }

    private fun getData() {
        viewModel.getProfile()
    }

    private fun setupSwipeRefreshLayout() {
        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener)
        swipeRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(requireContext(), R.color.primary_dark_blue_06)
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfile()
    }
}