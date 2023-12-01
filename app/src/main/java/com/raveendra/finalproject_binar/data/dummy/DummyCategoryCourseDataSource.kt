package com.raveendra.finalproject_binar.data.dummy

import com.raveendra.finalproject_binar.model.Course
import com.raveendra.finalproject_binar.model.CourseCategory

interface DummyCategoryCourseDataSource {
    fun getCategoryCourse(): List<CourseCategory>
}

class DummyCategoryCourseDataSourceImpl(): DummyCategoryCourseDataSource{
    override fun getCategoryCourse(): List<CourseCategory> = listOf(
        CourseCategory(
            imgCategoryCourse = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/Thumbnail.png",
            nameCourse = "UI/UX Design"
        ),
        CourseCategory(
            imgCategoryCourse = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/unsplash__x335IZXxfccc.png",
            nameCourse = "Product Management"
        ),
        CourseCategory(
            imgCategoryCourse = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/Thumbnail.png",
            nameCourse = "Web Development"
        ),
        CourseCategory(
            imgCategoryCourse = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/unsplash__x335IZXxfccc.png",
            nameCourse = "Android Development"
        )
    )

}