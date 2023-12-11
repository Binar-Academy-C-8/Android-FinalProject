package com.raveendra.finalproject_binar.data.request


import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(
    @SerializedName("code")
    val code: String?
)