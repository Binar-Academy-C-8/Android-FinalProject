package com.raveendra.finalproject_binar.data.response


import com.google.gson.annotations.SerializedName
import com.raveendra.finalproject_binar.domain.LoginDomain

data class LoginResponse(
    @SerializedName("data")
    val token: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)
fun LoginResponse.toDomain() = LoginDomain(
    message = this.message.orEmpty(),
    token = this.token.orEmpty(),
    status = this.status.orEmpty()
)