package com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Chapter(
    @SerializedName("chapterTitle")
    val chapterTitle: String? = null,
    @SerializedName("contents")
    val contents: List<Content?> ,
    @SerializedName("courseId")
    val courseId: Int? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("durationPerChapterInMinutes")
    val durationPerChapterInMinutes: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null
)