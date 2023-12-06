package com.raveendra.finalproject_binar.data.response


import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("categoryName")
    val categoryName: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: Any?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)
fun CategoryResponse.toDomain() = CategoryDomain(
    categoryName = this.categoryName,
    createdAt = this.createdAt,
    id = this.id,
    image = this.image,
    updatedAt = this.updatedAt
)

fun Collection<CategoryResponse>.toDomain() = this.map {
    it.toDomain()
}