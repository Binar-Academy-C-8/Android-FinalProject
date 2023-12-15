package com.raveendra.finalproject_binar.domain


data class LoginDomain(
    val message: String,
    val status: String,
    val data : LoginDataDomain?
)
data class LoginDataDomain(
    val token: String
)