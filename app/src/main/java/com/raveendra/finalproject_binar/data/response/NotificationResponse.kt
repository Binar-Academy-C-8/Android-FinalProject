package com.raveendra.finalproject_binar.data.response


import com.google.gson.annotations.SerializedName
import com.raveendrag.finalproject_binar.domain.DataDomain
import com.raveendrag.finalproject_binar.domain.NotificationReadDomain
import com.raveendrag.finalproject_binar.domain.NotificationResponseDomain
import com.raveendrag.finalproject_binar.domain.UserDomain

data class NotificationResponse(
    @SerializedName("data")
    val data : List<Data>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)

data class Data(
    @SerializedName("Course")
    val course: Any?,
    @SerializedName("courseId")
    val courseId: Any?,
    @SerializedName("CourseUser")
    val courseUser: Any?,
    @SerializedName("courseUserId")
    val courseUserId: Any?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("notificationRead")
    val notificationRead: List<NotificationRead?>?,
    @SerializedName("titleNotification")
    val titleNotification: String?,
    @SerializedName("typeNotification")
    val typeNotification: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("User")
    val user: User?,
    @SerializedName("userId")
    val userId: Int?
)

data class User(
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)

data class NotificationRead(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isRead")
    val isRead: Boolean?,
    @SerializedName("notifId")
    val notifId: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("userId")
    val userId: Int?
)

fun NotificationResponse.toDomain() = NotificationResponseDomain(
    data = this.data?.map { it.toDomain() },
    message = this.message.orEmpty(),
    status = this.status.orEmpty()
)

fun Data.toDomain() = DataDomain(
    course = this.course.toString(),
    courseId = this.courseId.toString(),
    courseUser = this.courseUser.toString(),
    courseUserId = this.courseUserId.toString(),
    createdAt = this.createdAt.orEmpty(),
    description = this.description.orEmpty(),
    id = this.id ?: 0,
    notificationRead = this.notificationRead?.map { it?.toDomain() },
    titleNotification = this.titleNotification.orEmpty(),
    typeNotification = this.typeNotification.orEmpty(),
    updatedAt = this.updatedAt.orEmpty(),
    user = this.user?.toDomain(),
    userId = this.userId ?: 0
)

fun User.toDomain() = UserDomain(
    city = this.city.orEmpty(),
    country = this.country.orEmpty(),
    createdAt = this.createdAt.orEmpty(),
    id = this.id ?: 0,
    image = this.image.orEmpty(),
    name = this.name.orEmpty(),
    phoneNumber = this.phoneNumber.orEmpty(),
    role = this.role.orEmpty(),
    updatedAt = this.updatedAt.orEmpty()
)

fun NotificationRead.toDomain() = NotificationReadDomain(
    createdAt = this.createdAt.orEmpty(),
    id = this.id ?: 0,
    isRead = this.isRead as Boolean ?: false,
    notifId = this.notifId ?: 0,
    updatedAt = this.updatedAt.orEmpty(),
    userId = this.userId ?: 0
)