package com.raveendra.finalproject_binar.presentation.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.raveendra.finalproject_binar.utils.ApiException
import com.raveendra.finalproject_binar.utils.NoInternetException
import com.raveendra.finalproject_binar.utils.base.BaseFragment
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<FragmentAccountBinding>()  {

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
        llMyProfile.setOnClickListener {
            ProfileActivity.navigate(requireContext())
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

    private fun setupObservers(){
        viewModel.resultProfile.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = { result ->
                binding.changePassword.setOnClickListener {
                    result.payload?.data?.id?.let { it1 ->
                        ChangePasswordActivity.navigate(requireContext(),
                            it1
                        )
                    }
                }
                binding.ivProfile.load(R.drawable.bg_button_dark_blue){
                    error(R.color.primary_dark_blue_06)
                    transformations(
                        CircleCropTransformation()
                    )
                    crossfade(true)
                }
                binding.tvName.text = result.payload?.data?.name ?: "-"
                binding.tvEmail.text = result.payload?.data?.email ?: "-"
                binding.swipeRefreshLayout.isRefreshing = false
            }, doOnLoading = {

            }, doOnError = { error ->
                if (error.exception is ApiException) {
                    if (error.exception.httpCode == 500){
                        binding.swipeRefreshLayout.isVisible = false
                        binding.clNotLogin.isVisible = true
                    }else{
                        val exceptionMessage = error.exception.getParsedError()?.message
                        if (!exceptionMessage.isNullOrBlank()){
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
                binding.swipeRefreshLayout.isRefreshing = false
            }, doOnEmpty = {
                binding.swipeRefreshLayout.isRefreshing = false

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
}