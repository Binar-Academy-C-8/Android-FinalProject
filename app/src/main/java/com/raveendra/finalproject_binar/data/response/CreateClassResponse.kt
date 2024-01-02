package com.raveendra.finalproject_binar.data.response


import com.google.gson.annotations.SerializedName
import com.raveendra.finalproject_binar.domain.CreateClassDataDomain
import com.raveendra.finalproject_binar.domain.CreateClassDomain

data class CreateClassResponse(
    @SerializedName("data")
    val `data`: CreateClassDataResponse?,
    @SerializedName("status")
    val status: String?
)
data class CreateClassDataResponse(
    @SerializedName("aboutCourse")
    val aboutCourse: String?,
    @SerializedName("categoryId")
    val categoryId: Int?,
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
    @SerializedName("courseType")
    val courseType: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("intendedFor")
    val intendedFor: String?,
    @SerializedName("isDiscount")
    val isDiscount: Boolean?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("telegramGroup")
    val telegramGroup: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)
fun CreateClassResponse.toDomain() = CreateClassDomain(
    status = this.status.orEmpty(),
    data = this.data?.toDomain()
)
fun CreateClassDataResponse.toDomain() = CreateClassDataDomain(
    id = this.id ?:0
)