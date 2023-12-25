package com.raveendra.finalproject_binar.domain

data class HistoryPaymentDomain(
    val status: String?,
    val userTransactions: List<UserTransactionDomain>?
)
data class UserTransactionDomain(
    val course: CoursePaymentDomain?,
    val courseId: Int?,
    val createdAt: String?,
    val id: Int?,
    val linkPayment: String?,
    val orderId: Int?,
    val paymentMethod: String?,
    val paymentStatus: String?,
    val ppn: Int?,
    val price: Int?,
    val totalPrice: Int?,
    val updatedAt: String?,
    val userId: Int?
)

data class CoursePaymentDomain(
    val aboutCourse: String?,
    val adminId: Int?,
    val categoryId: Int?,
    val courseCode: String?,
    val courseLevel: String?,
    val courseName: String?,
    val coursePrice: Int?,
    val courseType: String?,
    val createdAt: String?,
    val id: Int?,
    val image: String?,
    val intendedFor: String?,
    val rating: Double?,
    val updatedAt: String?
)


