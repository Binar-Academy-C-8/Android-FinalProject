package com.raveendra.finalproject_binar.data.response

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)

