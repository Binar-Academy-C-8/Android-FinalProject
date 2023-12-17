package com.raveendra.finalproject_binar.data.response


import com.google.gson.annotations.SerializedName
import com.raveendra.finalproject_binar.domain.CreatedTransactionDataDomain
import com.raveendra.finalproject_binar.domain.TransactionDomain

data class TransactionResponse(
    @SerializedName("createdTransactionData")
    val createdTransactionData: CreatedTransactionDataResponse?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("url")
    val url: String?
)

data class CreatedTransactionDataResponse(
    @SerializedName("courseId")
    val courseId: Int?,
    @SerializedName("courseName")
    val courseName: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
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

fun TransactionResponse.toDomain() = TransactionDomain(
    createdTransactionData = this.createdTransactionData?.toDomain(),
    email = this.email.orEmpty(),
    status = this.status.orEmpty(),
    token = this.token.orEmpty(),
    url = this.url.orEmpty()
)

fun CreatedTransactionDataResponse.toDomain() = CreatedTransactionDataDomain(
    courseId = this.courseId ?: 0,
    courseName = this.courseName.orEmpty(),
    createdAt = this.createdAt.orEmpty(),
    id = this.courseId ?: 0,
    orderId = this.orderId ?: 0,
    paymentMethod = this.paymentMethod.orEmpty(),
    paymentStatus = this.paymentStatus.orEmpty(),
    ppn = this.ppn ?: 0,
    price = this.price ?: 0,
    totalPrice = this.totalPrice ?: 0,
    updatedAt = this.updatedAt.orEmpty(),
    userId = this.userId ?: 0,
)


