package com.raveendra.finalproject_binar.data.dummy

import com.raveendra.finalproject_binar.domain.CourseDomain

interface DummyCoursePremium {
    fun getCoursePremium() : List<CourseDomain>
}

class DummyCoursePremiumImpl(): DummyCoursePremium{
    override fun getCoursePremium(): List<CourseDomain> = listOf(
        CourseDomain(
            aboutCourse = "ui",
            category = "UI/UX Design",
            categoryId = 2,
            courseBy = "adminc8",
            courseCode = "uiux123",
            courseLevel = "beginner",
            courseName = "UI/UX for Expert",
            coursePrice = 450000,
            courseType = "premium",
            createdAt = "2023-11-06",
            durationPerCourseInMinutes = 5,
            id = 2,
            image = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/Thumbnail.png",
            intendedFor = "unutk sepuh yang berkedok pemula",
            modulePerCourse = 5,
            updatedAt ="2023-11-06" ,
            userId = 2
        )
    )
}