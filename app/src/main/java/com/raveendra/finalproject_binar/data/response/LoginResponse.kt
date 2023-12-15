package com.raveendra.finalproject_binar.data.response

import com.google.gson.annotations.SerializedName
import com.raveendra.finalproject_binar.domain.LoginDataDomain
import com.raveendra.finalproject_binar.domain.LoginDomain

data class LoginResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("data")
    val data: LoginDataResponse? = null
)
data class LoginDataResponse(
    @SerializedName("token")
    val token: String? = null
)

fun LoginResponse.toDomain() = LoginDomain(
    message = this.message.orEmpty(),
    status = this.status.orEmpty(),
    data = this.data?.toDomain()
)

fun LoginDataResponse.toDomain() = LoginDataDomain(
    token = this.token.orEmpty()
)

