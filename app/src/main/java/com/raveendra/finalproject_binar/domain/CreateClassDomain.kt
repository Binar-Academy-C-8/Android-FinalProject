package com.raveendra.finalproject_binar.domain


data class CreateClassDomain(
    val `data`: CreateClassDataDomain?,
    val status: String?
)
data class CreateClassDataDomain(
    val id: Int?,

)