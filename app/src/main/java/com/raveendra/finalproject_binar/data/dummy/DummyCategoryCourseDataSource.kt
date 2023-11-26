package com.raveendra.finalproject_binar.data.dummy

import com.raveendra.finalproject_binar.model.Course
import com.raveendra.finalproject_binar.model.CourseCategory

interface DummyCategoryCourseDataSource {
    fun getCategoryCourse(): List<CourseCategory>
}

class DummyCategoryCourseDataSourceImpl(): DummyCategoryCourseDataSource{
    override fun getCategoryCourse(): List<CourseCategory> = listOf(
        CourseCategory(
            imgCategoryCourse = "https://raw.githubusercontent.com/panipujayanti/FinalProjectAsset/master/app/src/main/res/drawable/ic_ui_ux.png",
            nameCourse = "UI/UX Design"
        ),
        CourseCategory(
            imgCategoryCourse = "https://raw.githubusercontent.com/panipujayanti/FinalProjectAsset/master/app/src/main/res/drawable/ic_pm.png",
            nameCourse = "Product Management"
        ),
        CourseCategory(
            imgCategoryCourse = "https://raw.githubusercontent.com/panipujayanti/FinalProjectAsset/master/app/src/main/res/drawable/ic_web_development.png",
            nameCourse = "Web Development"
        ),
        CourseCategory(
            imgCategoryCourse = "https://raw.githubusercontent.com/panipujayanti/FinalProjectAsset/master/app/src/main/res/drawable/ic_android_development.png",
            nameCourse = "Android Development"
        )
    )

}