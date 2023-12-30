package com.raveendra.finalproject_binar.data.request

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(

    @SerializedName("oldPassword")
    val oldPassword: String?,
    @SerializedName("newUserPassword")
    val newUserPassword: String?,
    @SerializedName("confirmPassword")
    val confirmPassword: String?
)
