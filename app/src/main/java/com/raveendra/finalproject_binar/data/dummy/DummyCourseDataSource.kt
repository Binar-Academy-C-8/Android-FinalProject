package com.raveendra.finalproject_binar.data.dummy

import com.raveendra.finalproject_binar.model.Course

interface DummyCourseDataSource {
    fun getCourseList(): List<Course>
}

class DummyCourseDataSourceImpl() : DummyCourseDataSource {
    override fun getCourseList(): List<Course> = listOf(
        Course(
            name = "Intro to Basic of User Interaction Design",
            imgUrl = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/unsplash__x335IZXxfccc.png",
            author = "Maman",
            rating = 4.8,
            level = "Intermediate Level",
            modul = "11 Modul",
            duration = "120 Menit"
        ),
        Course(
            name = "Intro to Basic of User Interaction Design",
            imgUrl = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/unsplash__x335IZXxfccc.png",
            author = "Jack",
            rating = 4.3,
            level = "Advanced Level",
            modul = "8 Modul",
            duration = "60 Menit"
        ),
        Course(
            name = "Intro to Basic of User Interaction Design",
            imgUrl = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/unsplash__x335IZXxfccc.png",
            author = "Maman",
            rating = 4.5,
            level = "Intermediate Level",
            modul = "11 Modul",
            duration = "120 Menit"
        )
    )
}