package com.raveendrag.finalproject_binar.domain

data class NotificationResponseDomain(
    val data : List<DataDomain>?,
    val message: String?,
    val status: String?
)

data class DataDomain(
    val course: Any?,
    val courseId: Any?,
    val courseUser: Any?,
    val courseUserId: Any?,
    val createdAt: String?,
    val description: String?,
    val id: Int?,
    val notificationRead: List<NotificationReadDomain?>?,
    val titleNotification: String?,
    val typeNotification: String?,
    val updatedAt: String?,
    val user: UserDomain?,
    val userId: Int?
)

data class UserDomain(
    val city: String?,
    val country: String?,
    val createdAt: String?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val phoneNumber: String?,
    val role: String?,
    val updatedAt: String?
)

data class NotificationReadDomain(
    val createdAt: String?,
    val id: Int?,
    val isRead: Boolean?,
    val notifId: Int?,
    val updatedAt: String?,
    val userId: Int?
)