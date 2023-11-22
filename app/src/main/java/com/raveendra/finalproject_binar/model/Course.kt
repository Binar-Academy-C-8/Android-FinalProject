package com.raveendra.finalproject_binar.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Course(
    val id: Int? = null,
    val name: String,
    val imgUrl: String,
    val author: String,
    val rating: Double,
    val level: String,
    val modul: String,
    val duration: String,
): Parcelable