package com.raveendra.finalproject_binar.domain


data class TransactionDomain(
    val createdTransactionData: CreatedTransactionDataDomain?,
    val email: String,
    val status: String,
    val token: String,

)
data class CreatedTransactionDataDomain(
    val courseId: Int,
    val courseName: String,
    val createdAt: String,
    val id: Int,
    val orderId: Int,
    val paymentMethod: String,
    val paymentStatus: String,
    val ppn: Int,
    val price: Int,
    val totalPrice: Int,
    val updatedAt: String,
    val userId: Int,
    val linkPayment: String
)