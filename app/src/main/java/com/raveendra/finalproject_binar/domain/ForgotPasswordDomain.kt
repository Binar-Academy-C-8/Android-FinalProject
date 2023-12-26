package com.raveendra.finalproject_binar.domain

import com.raveendra.finalproject_binar.data.request.NewOtpRequest

/**
 *hrahm,20/12/2023, 20:39
 **/
data class ForgotPasswordDomain(
    val status: String,
    val message: String,
    val data: NewOtpRequest
)
