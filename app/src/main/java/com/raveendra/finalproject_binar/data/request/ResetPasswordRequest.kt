package com.raveendra.finalproject_binar.data.request

import com.google.gson.annotations.SerializedName

/**
 *hrahm,25/12/2023, 20:59
 **/
data class ResetPasswordRequest(
    @SerializedName("password")
    val password: String?
)
