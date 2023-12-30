package com.raveendra.finalproject_binar.data.response


import com.google.gson.annotations.SerializedName
import com.raveendra.finalproject_binar.domain.UpdateClassProgressDomain

data class UpdateClassProgressResponse(
    @SerializedName("contentFinished")
    val contentFinished: Int? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("message")
    val message: String? = null
)
fun UpdateClassProgressResponse.toDomain() = UpdateClassProgressDomain(
    contentFinished = this.contentFinished ?: 0,
    status = this.status.orEmpty(),
    message = this.message.orEmpty()
)