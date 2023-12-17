package com.raveendra.finalproject_binar.data.response


import com.google.gson.annotations.SerializedName
import com.raveendra.finalproject_binar.domain.ProfileDataDomain
import com.raveendra.finalproject_binar.domain.ProfileDomain

data class ProfileResponse(
    @SerializedName("data")
    val data: ProfileDataResponse?,
    @SerializedName("status")
    val status: String?
)
data class ProfileDataResponse(
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?
)
fun ProfileResponse.toDomain() = ProfileDomain(
    data = this.data?.toDomain(),
    status = this.status.orEmpty()
)

fun ProfileDataResponse.toDomain() = ProfileDataDomain(
    city = this.city.orEmpty(),
    country = this.country.orEmpty(),
    email = this.email.orEmpty(),
    id = this.id ?: 0,
    image = this.image.orEmpty(),
    name = this.name.orEmpty(),
    phoneNumber = this.phoneNumber.orEmpty(),
)
