package com.raveendra.finalproject_binar.utils

import com.google.gson.annotations.SerializedName

data class ResponseListWrapper<T : Any>(
    @SerializedName(value ="data",alternate= ["courses"])
    val data: List<T>?,
    @SerializedName("status")
    val status: Boolean?
)
