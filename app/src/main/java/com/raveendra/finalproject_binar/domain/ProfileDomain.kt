package com.raveendra.finalproject_binar.domain


import com.google.gson.annotations.SerializedName

data class ProfileDomain(
    @SerializedName("data")
    val data: ProfileDataDomain?,
    @SerializedName("status")
    val status: String?
)
data class ProfileDataDomain(
    @SerializedName("city")
    val city: Any?,
    @SerializedName("country")
    val country: Any?,
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