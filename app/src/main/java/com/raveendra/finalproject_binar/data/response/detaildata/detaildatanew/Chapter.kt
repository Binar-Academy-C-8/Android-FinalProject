package com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Chapter(
    @SerializedName("chapterTitle")
    val chapterTitle: String,
    @SerializedName("contents")
    val contents: List<Content>,
    @SerializedName("courseId")
    val courseId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("durationPerChapterInMinutes")
    val durationPerChapterInMinutes: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)