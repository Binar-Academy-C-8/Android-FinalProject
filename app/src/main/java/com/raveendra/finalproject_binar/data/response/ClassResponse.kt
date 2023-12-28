package com.raveendra.finalproject_binar.data.response


import com.google.gson.annotations.SerializedName
import com.raveendra.finalproject_binar.domain.ClassDomain

data class ClassResponse(
    @SerializedName("category")
    val category: String?,
    @SerializedName("categoryId")
    val categoryId: Int?,
    @SerializedName("contentFinished")
    val contentFinished: Int?,
    @SerializedName("contentTotal")
    val contentTotal: Int?,
    @SerializedName("courseBy")
    val courseBy: String?,
    @SerializedName("courseCode")
    val courseCode: String?,
    @SerializedName("courseDiscountInPercent")
    val courseDiscountInPercent: Int?,
    @SerializedName("courseLevel")
    val courseLevel: String?,
    @SerializedName("courseName")
    val courseName: String?,
    @SerializedName("coursePrice")
    val coursePrice: Int?,
    @SerializedName("courseProgressInPercentage")
    val courseProgressInPercentage: Int?,
    @SerializedName("courseStatus")
    val courseStatus: String?,
    @SerializedName("courseType")
    val courseType: String?,
    @SerializedName("courseUserId")
    val courseUserId: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("durationPerCourseInMinutes")
    val durationPerCourseInMinutes: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("isDiscount")
    val isDiscount: Boolean?,
    @SerializedName("modulePerCourse")
    val modulePerCourse: Int?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("rawPrice")
    val rawPrice: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: Int?
)
fun ClassResponse.toDomain() = ClassDomain(
    category = this.category.orEmpty(),
    categoryId = this.categoryId ?: 0,
    contentFinished = this.contentFinished ?: 0,
    contentTotal = this.contentTotal ?: 0,
    courseBy = this.courseBy.orEmpty(),
    courseCode = this.courseCode.orEmpty(),
    courseDiscountInPercent = this.courseDiscountInPercent ?: 0,
    courseLevel = this.courseLevel.orEmpty(),
    courseName = this.courseName.orEmpty(),
    coursePrice = this.coursePrice ?: 0,
    courseProgressInPercentage = this.courseProgressInPercentage ?: 0,
    courseStatus = this.courseStatus.orEmpty(),
    courseType = this.courseType.orEmpty(),
    courseUserId = this.courseUserId ?: 0,
    createdAt = this.createdAt.orEmpty(),
    durationPerCourseInMinutes = this.durationPerCourseInMinutes ?: 0,
    id = this.id ?: 0,
    image = this.image.orEmpty(),
    isDiscount = this.isDiscount ?: false,
    modulePerCourse = this.modulePerCourse ?: 0,
    rating = this.rating ?: 0.0,
    rawPrice = this.rawPrice ?: 0,
    updatedAt = this.updatedAt.orEmpty(),
    userId = this.userId ?: 0,

)

fun Collection<ClassResponse>.toDomain() = this.map {
    it.toDomain()
}