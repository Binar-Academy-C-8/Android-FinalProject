package com.raveendra.finalproject_binar.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CourseCategory(
    val id: Int? = null,
    val imgCategoryCourse: String,
    val nameCourse: String,
) : Parcelable
