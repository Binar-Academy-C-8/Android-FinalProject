package com.raveendra.finalproject_binar.data.response


import com.google.gson.annotations.SerializedName
import com.raveendra.finalproject_binar.domain.RegisterDataDomain
import com.raveendra.finalproject_binar.domain.RegisterDataValuesDomain
import com.raveendra.finalproject_binar.domain.RegisterDomain

data class RegisterResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val data: RegisterDataResponse?,
    @SerializedName("status")
    val status: String? = null
)
data class RegisterDataResponse(
    @SerializedName("dataValues")
    val dataValues: RegisterDataValuesResponse?,
)
data class RegisterDataValuesResponse(
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
fun RegisterResponse.toDomain() = RegisterDomain(
    message = this.message.orEmpty(),
    data = this.data?.toDomain(),
    status = this.status.orEmpty()
)
fun RegisterDataResponse.toDomain() = RegisterDataDomain(
    dataValues = this.dataValues?.toDomain()
)
fun RegisterDataValuesResponse.toDomain() = RegisterDataValuesDomain(
    id = this.id
)