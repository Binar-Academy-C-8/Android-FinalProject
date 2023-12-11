package com.raveendra.finalproject_binar.data.response


import com.google.gson.annotations.SerializedName
import com.raveendra.finalproject_binar.domain.NewOtpDataDomain
import com.raveendra.finalproject_binar.domain.NewOtpDomain
import com.raveendra.finalproject_binar.domain.NewOtpRequestDomain

data class NewOtpResponse(
    @SerializedName("data")
    val data: NewOtpDataResponse?,
    @SerializedName("status")
    val status: String?
)

data class NewOtpDataResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("newOtpRequest")
    val newOtpRequest: NewOtpRequestResponse?
)

data class NewOtpRequestResponse(
    @SerializedName("code")
    val code: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("expiredAt")
    val expiredAt: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: Int?
)

fun NewOtpResponse.toDomain() = NewOtpDomain(
    data = this.data?.toDomain(),
    status = this.status.orEmpty()
)

fun NewOtpDataResponse.toDomain() = NewOtpDataDomain(
    message = this.message.orEmpty(),
    newOtpRequest = this.newOtpRequest?.toDomain()
)

fun NewOtpRequestResponse.toDomain() = NewOtpRequestDomain(
    userId = this.userId ?: 0
)