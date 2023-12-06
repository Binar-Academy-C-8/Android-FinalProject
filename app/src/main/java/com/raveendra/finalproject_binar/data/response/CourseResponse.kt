package com.raveendra.finalproject_binar.data.response


import com.raveendra.finalproject_binar.domain.CourseDomain
import com.google.gson.annotations.SerializedName

data class CourseResponse(
    @SerializedName("aboutCourse")
    val aboutCourse: String?,
    @SerializedName("Category")
    val category: String?,
    @SerializedName("categoryId")
    val categoryId: Int?,
    @SerializedName("courseBy")
    val courseBy: String?,
    @SerializedName("courseCode")
    val courseCode: String?,
    @SerializedName("courseLevel")
    val courseLevel: String?,
    @SerializedName("courseName")
    val courseName: String?,
    @SerializedName("coursePrice")
    val coursePrice: Int?,
    @SerializedName("courseType")
    val courseType: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("durationPerCourseInMinutes")
    val durationPerCourseInMinutes: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("intendedFor")
    val intendedFor: String?,
    @SerializedName("modulePerCourse")
    val modulePerCourse: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: Int?
)

fun CourseResponse.toDomain() = CourseDomain(
    aboutCourse = this.aboutCourse,
    category = this.category,
    categoryId = this.categoryId,
    courseBy = this.courseBy,
    courseCode = this.courseCode,
    courseLevel = this.courseLevel,
    courseName = this.courseName,
    coursePrice = this.coursePrice,
    courseType = this.courseType,
    createdAt = this.createdAt,
    durationPerCourseInMinutes = this.durationPerCourseInMinutes,
    id = this.id,
    image = this.image,
    intendedFor = this.intendedFor,
    modulePerCourse = this.modulePerCourse,
    updatedAt = this.updatedAt,
    userId = this.userId,
)

fun Collection<CourseResponse>.toDomain() = this.map {
    it.toDomain()
}