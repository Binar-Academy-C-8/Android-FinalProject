package com.raveendra.finalproject_binar.utils

import com.google.gson.annotations.SerializedName

data class ResponseListWrapper<T : Any>(
    @SerializedName("data")
    val data: List<T>?,
    @SerializedName("status")
    val status: Boolean?
)
