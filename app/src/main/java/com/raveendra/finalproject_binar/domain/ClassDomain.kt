package com.raveendra.finalproject_binar.domain


data class ClassDomain(
    val category: String,
    val categoryId: Int,
    val contentFinished: Int,
    val contentTotal: Int,
    val courseBy: String,
    val courseCode: String,
    val courseDiscountInPercent: Int,
    val courseLevel: String,
    val courseName: String,
    val coursePrice: Int,
    val courseProgressInPercentage: Int,
    val courseStatus: String,
    val courseType: String,
    val courseUserId: Int,
    val createdAt: String,
    val durationPerCourseInMinutes: Int,
    val id: Int,
    val image: String,
    val isDiscount: Boolean,
    val modulePerCourse: Int,
    val rating: Double,
    val rawPrice: Int,
    val updatedAt: String,
    val userId: Int
)