package com.raveendra.finalproject_binar.data.response.historypayment


import com.google.gson.annotations.SerializedName
import com.raveendra.finalproject_binar.domain.CoursePaymentDomain
import com.raveendra.finalproject_binar.domain.HistoryPaymentDomain
import com.raveendra.finalproject_binar.domain.UserTransactionDomain

data class HistoryPaymentResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("userTransactions")
    val userTransactions: List<UserTransactionResponse>?
)

data class UserTransactionResponse(
    @SerializedName("course")
    val course: CoursePaymentResponse?,
    @SerializedName("courseId")
    val courseId: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("linkPayment")
    val linkPayment: String?,
    @SerializedName("orderId")
    val orderId: Int?,
    @SerializedName("paymentMethod")
    val paymentMethod: String?,
    @SerializedName("paymentStatus")
    val paymentStatus: String?,
    @SerializedName("ppn")
    val ppn: Int?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("totalPrice")
    val totalPrice: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: Int?
)

data class CoursePaymentResponse(
    @SerializedName("aboutCourse")
    val aboutCourse: String?,
    @SerializedName("adminId")
    val adminId: Int?,
    @SerializedName("categoryId")
    val categoryId: Int?,
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
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("intendedFor")
    val intendedFor: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("updatedAt")
    val updatedAt: String?

)

fun HistoryPaymentResponse.toDomain() = HistoryPaymentDomain(
    status = this.status.orEmpty(),
    userTransactions = this.userTransactions?.map { it.toDomain() }
)


fun UserTransactionResponse.toDomain() = UserTransactionDomain(
    course = this.course?.toDomain(),
    courseId = this.courseId ?: 0,
    createdAt = this.createdAt.orEmpty(),
    id = this.id ?: 0,
    linkPayment = this.linkPayment.orEmpty(),
    orderId = this.orderId ?: 0,
    paymentMethod = this.paymentMethod.orEmpty(),
    paymentStatus = this.paymentStatus.orEmpty(),
    ppn = this.ppn ?: 0,
    price = this.price ?: 0,
    totalPrice = this.totalPrice ?: 0,
    updatedAt = this.updatedAt.orEmpty(),
    userId = this.userId ?: 0
    )

fun CoursePaymentResponse.toDomain() = CoursePaymentDomain(
    aboutCourse = this.aboutCourse.orEmpty(),
    adminId = this.adminId ?: 0,
    categoryId = this.categoryId ?: 0,
    courseCode = this.courseCode.orEmpty(),
    courseLevel = this.courseLevel.orEmpty(),
    courseName = this.courseName.orEmpty(),
    coursePrice = this.coursePrice ?: 0,
    courseType = this.courseType.orEmpty(),
    createdAt = this.createdAt.orEmpty(),
    id = this.id ?: 0,
    image = this.image.orEmpty(),
    intendedFor = this.intendedFor.orEmpty(),
    rating = this.rating ?: 0.0,
    updatedAt = this.updatedAt.orEmpty()
)
