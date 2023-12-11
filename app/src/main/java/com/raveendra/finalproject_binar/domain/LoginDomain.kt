package com.raveendra.finalproject_binar.domain


import com.google.gson.annotations.SerializedName

data class LoginDomain(
    @SerializedName("data")
    val token: String,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)