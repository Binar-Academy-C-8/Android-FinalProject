package com.raveendra.finalproject_binar.presentation.account.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import coil.load
import coil.transform.CircleCropTransformation
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityProfileBinding
import com.raveendra.finalproject_binar.utils.ApiException
import com.raveendra.finalproject_binar.utils.NoInternetException
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileActivity : BaseViewModelActivity<ProfileViewModel, ActivityProfileBinding>() {
    companion object {
        fun navigate(context: Context) = with(context) {
            startActivity(
                Intent(
                    this,
                    ProfileActivity::class.java
                )
            )
        }
    }
    override val viewModel: ProfileViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityProfileBinding
        get() = ActivityProfileBinding::inflate

    override fun setupViews()  {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivProfile.load(
            R.drawable.bg_button_dark_blue
        ){
            transformations(
                CircleCropTransformation()
            )
            crossfade(true)
        }
        binding.etProfileName.isEnabled = false
        binding.etProfileName.isClickable = false
        binding.etProfileEmail.isEnabled = false
        binding.etProfileEmail.isClickable = false
        binding.etProfilePhone.isEnabled = false
        binding.etProfilePhone.isClickable = false

        viewModel.getProfile()
    }

    override fun setupObservers() {
        viewModel.resultProfile.observe(this) {
            it.proceedWhen(doOnSuccess = { result ->
                binding.etProfileName.setText(result.payload?.data?.name ?: "-")
                binding.etProfileEmail.setText(result.payload?.data?.email ?: "-")
                binding.etProfilePhone.setText(result.payload?.data?.phoneNumber ?: "-")
            }, doOnLoading = {

            }, doOnError = { error ->
                if (error.exception is ApiException) {
                    val exceptionMessage = error.exception.getParsedError()?.message
                    Toast.makeText(
                        this@ProfileActivity,
                        exceptionMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (error.exception is NoInternetException) {
                    Toast.makeText(
                        this@ProfileActivity,
                        getString(R.string.label_error_no_internet),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, doOnEmpty = {

            })
        }
    }

}