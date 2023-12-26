package com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("aboutCourse")
    val aboutCourse: String? = null,
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("categoryId")
    val categoryId: Int? = null,
    @SerializedName("chapters")
    val chapters: List<Chapter>,
    @SerializedName("courseBy")
    val courseBy: String? = null,
    @SerializedName("courseCode")
    val courseCode: String? = null,
    @SerializedName("courseLevel")
    val courseLevel: String? = null,
    @SerializedName("courseName")
    val courseName: String? = null,
    @SerializedName("coursePrice")
    val coursePrice: Int? = null,
    @SerializedName("courseDiscountInPercent")
    val courseDiscountInPercent: Int? = null,
    @SerializedName("rawPrice")
    val rawPrice: Int? = null,
    @SerializedName("courseType")
    val courseType: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("durationPerCourseInMinutes")
    val durationPerCourseInMinutes: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("intendedFor")
    val intendedFor: String? = null,
    @SerializedName("modulePerCourse")
    val modulePerCourse: Int? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)