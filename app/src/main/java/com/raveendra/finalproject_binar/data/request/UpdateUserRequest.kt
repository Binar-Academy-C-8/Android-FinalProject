package com.raveendra.finalproject_binar.data.request

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

/**
 *hrahm,29/12/2023, 16:33
 **/
data class UpdateUserRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("image")
    val image: MultipartBody.Part
)
