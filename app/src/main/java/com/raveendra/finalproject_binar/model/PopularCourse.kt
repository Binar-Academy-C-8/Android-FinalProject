package com.raveendra.finalproject_binar.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PopularCourse(
    val id: Int? = null,
    val imgPopularCourse: String,
    val namePopularCourse: String,
    val ratingCourse: String,
    val titleCourse: String,
    val authorCourse: String,
    val levelCourse: String,
    val durationCourse: String,
    val moduleCourse: String,
    val priceCourse: Double

) : Parcelable
