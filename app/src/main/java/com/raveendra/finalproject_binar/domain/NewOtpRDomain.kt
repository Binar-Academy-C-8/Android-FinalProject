package com.raveendra.finalproject_binar.domain


data class NewOtpDomain(
    val data: NewOtpDataDomain?,
    val status: String
)
data class NewOtpDataDomain(
    val message: String,
    val newOtpRequest: NewOtpRequestDomain?
)
data class NewOtpRequestDomain(
    val userId: Int
)