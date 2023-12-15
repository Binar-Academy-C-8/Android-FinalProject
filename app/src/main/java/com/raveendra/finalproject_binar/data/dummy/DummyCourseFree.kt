package com.raveendra.finalproject_binar.data.dummy

import com.raveendra.finalproject_binar.domain.CourseDomain

interface DummyCourseFree {
    fun getCourseFree(): List<CourseDomain>
}

class DummyCourseFreeImpl(): DummyCourseFree{
    override fun getCourseFree(): List<CourseDomain> = listOf(
        CourseDomain(
            aboutCourse = "ui",
            category = "UI/UX Design",
            categoryId = 1,
            courseBy = "adminc8",
            courseCode = "uiux123",
            courseLevel = "beginner",
            courseName = "UI/UX for Beginner",
            coursePrice = 0,
            courseType = "free",
            createdAt = "2023-11-06",
            durationPerCourseInMinutes = 5,
            id = 1,
            image = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/Thumbnail.png",
            intendedFor = "unutk pemula yang belum menjadi sepuh",
            modulePerCourse = 5,
            updatedAt ="2023-11-06" ,
            userId = 1,
            ratingCourse = null
        )
    )
}