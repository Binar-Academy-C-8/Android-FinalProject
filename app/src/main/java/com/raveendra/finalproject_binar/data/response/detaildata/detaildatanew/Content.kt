package com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Content(
    @SerializedName("chapterId")
    val chapterId: Int? = null,
    @SerializedName("contentTitle")
    val contentTitle: String? = null,
    @SerializedName("contentUrl")
    val contentUrl: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("duration")
    val duration: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("status")
    val status: Boolean? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("youtubeId")
    val youtubeId: String? = null
)