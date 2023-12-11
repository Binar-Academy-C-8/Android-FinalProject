package com.raveendra.finalproject_binar.data.request


import com.google.gson.annotations.SerializedName

data class NewOtpRequest(
    @SerializedName("email")
    val email: String?
)