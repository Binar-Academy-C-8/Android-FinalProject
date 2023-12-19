package com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("aboutCourse")
    val aboutCourse: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("chapters")
    val chapters: List<Chapter>,
    @SerializedName("courseBy")
    val courseBy: String,
    @SerializedName("courseCode")
    val courseCode: String,
    @SerializedName("courseLevel")
    val courseLevel: String,
    @SerializedName("courseName")
    val courseName: String,
    @SerializedName("coursePrice")
    val coursePrice: Int,
    @SerializedName("courseType")
    val courseType: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("durationPerCourseInMinutes")
    val durationPerCourseInMinutes: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("intendedFor")
    val intendedFor: String,
    @SerializedName("modulePerCourse")
    val modulePerCourse: Int,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("userId")
    val userId: Int
)