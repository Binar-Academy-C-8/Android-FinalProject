package com.raveendra.finalproject_binar.data.response

import com.google.gson.annotations.SerializedName
import com.raveendra.finalproject_binar.domain.StatusMessageDomain

data class BaseResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null
)
fun BaseResponse.toDomain() = StatusMessageDomain(
    message = this.message.orEmpty(),
    status = this.status.orEmpty()
)

