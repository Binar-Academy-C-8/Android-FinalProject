package com.raveendra.finalproject_binar.presentation.account.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.net.toFile
import coil.load
import coil.transform.CircleCropTransformation
import com.github.dhaval2404.imagepicker.ImagePicker
import com.raveendra.finalproject_binar.R
import com.raveendra.finalproject_binar.databinding.ActivityProfileBinding
import com.raveendra.finalproject_binar.utils.ApiException
import com.raveendra.finalproject_binar.utils.NoInternetException
import com.raveendra.finalproject_binar.utils.base.BaseViewModelActivity
import com.raveendra.finalproject_binar.utils.proceedWhen
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class ProfileActivity : BaseViewModelActivity<ProfileViewModel, ActivityProfileBinding>() {
    companion object {
        const val EXTRA_ID = "EXTRA_ID"
        fun navigate(context: Context, id: Int) = with(context) {
            startActivity(
                Intent(
                    this,
                    ProfileActivity::class.java
                ).putExtra(EXTRA_ID, id)
            )
        }
    }

    private val idExtra by lazy {
        intent.getIntExtra(EXTRA_ID, 0)
    }
    override val viewModel: ProfileViewModel by viewModel()
    private var getFile: File? = null
    override val bindingInflater: (LayoutInflater) -> ActivityProfileBinding
        get() = ActivityProfileBinding::inflate


    private fun imagePicker() {
        ImagePicker.with(this)
            .cropSquare()
            .galleryOnly()
            .compress(1024)
            .maxResultSize(1000, 1000)
            .start()

    }


    override fun setupViews() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivProfile.load(
            R.drawable.bg_button_dark_blue
        ) {
            error(R.color.primary_dark_blue_06)
            transformations(
                CircleCropTransformation()
            )
            crossfade(true)
        }
        binding.etProfileName.isEnabled = false
        binding.etProfileName.isClickable = false
        binding.etProfilePhone.isEnabled = false
        binding.etProfilePhone.isClickable = false

        viewModel.getProfile()
        binding.ivProfile.setOnClickListener {
            imagePicker()
        }
        binding.ivChangeProfile.setOnClickListener {
            imagePicker()
        }
        binding.btnUpdate.setOnClickListener {
            updateProfile()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfile()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            val img = uri?.toFile()
            binding.ivProfile.load(uri)
            getFile = img
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateProfile() {
        val imageFile = getFile
        if (imageFile != null) {
            val imageRequestBody =
                imageFile.asRequestBody("image/jpg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part? = imageRequestBody?.let {
                MultipartBody.Part.createFormData(
                    "image",
                    imageFile.name,
                    it
                )
            }

            val name = binding.etProfileName.getText().toString().trim()
            val phone = binding.etProfilePhone.getText().toString().trim()

            if (imageMultipart != null) {
                viewModel.updateProfile(
                    imageMultipart,
                    idExtra,
                    name.toRequestBody("multiplatform/form-data".toMediaTypeOrNull()),
                    phone.toRequestBody("multiplatform/form-data".toMediaTypeOrNull()),
                )
            }
        }
    }

    override fun setupObservers() {
        viewModel.resultProfile.observe(this) {
            it.proceedWhen(doOnSuccess = { result ->
                binding.ivProfile.load(result.payload?.data?.image) {
                    placeholder(R.color.primary_dark_blue_06)
                    transformations(
                        CircleCropTransformation()
                    )
                    crossfade(true)
                }
                binding.etProfileName.setText(result.payload?.data?.name ?: "-")
                binding.tvEmail.text = result.payload?.data?.email ?: "-"
                binding.etProfilePhone.setText(result.payload?.data?.phoneNumber ?: "-")
            }, doOnLoading = {

            }, doOnError = { error ->
                if (error.exception is ApiException) {
                    val exceptionMessage = error.exception.getParsedError()?.message
                    Toast.makeText(
                        this@ProfileActivity,
                        "${it.payload?.status.toString()} ",
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

        viewModel.updateProfile.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, "${it.payload?.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }


}