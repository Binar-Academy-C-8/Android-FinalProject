package com.raveendra.finalproject_binar.data.request

import com.google.gson.annotations.SerializedName

/**
 *hrahm,20/12/2023, 20:30
 **/
data class ForgotPasswordRequest(
    @SerializedName("email")
    val email: String
)
