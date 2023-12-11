package com.raveendra.finalproject_binar.domain

import com.google.gson.annotations.SerializedName


data class RegisterDomain(
    val message: String,
    val data: RegisterDataDomain?,
    val status: String
)
data class RegisterDataDomain(
    @SerializedName("dataValues")
    val dataValues: RegisterDataValuesDomain?,
)
data class RegisterDataValuesDomain(
    @SerializedName("id")
    val id: Int?
)