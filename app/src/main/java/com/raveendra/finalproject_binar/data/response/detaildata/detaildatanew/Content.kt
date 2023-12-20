package com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Content(
    @SerializedName("chapterId")
    val chapterId: Int,
    @SerializedName("contentTitle")
    val contentTitle: String,
    @SerializedName("contentUrl")
    val contentUrl: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("youtubeId")
    val youtubeId: String
)