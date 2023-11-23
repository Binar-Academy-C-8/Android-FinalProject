package com.raveendra.finalproject_binar.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Notification (
    val id: Int? = null,
    val image: String,
    val label: String,
    val date: String,
    val elipse: String,
    val text: String,
    val description: String

) : Parcelable