package com.raveendra.finalproject_binar.data.dummy

import com.raveendra.finalproject_binar.model.Course

interface DummyCategoryCourseDataSource {
    fun getCategoryCourse(): List<Course>
}

class DummyCategoryCourseDataSourceImpl(): DummyCategoryCourseDataSource{
    override fun getCategoryCourse(): List<Course> = listOf(
        Course(
            imgCategoryCourse = "https://raw.githubusercontent.com/panipujayanti/FinalProjectAsset/master/app/src/main/res/drawable/ic_ui_ux.png",
            nameCourse = "UI/UX Design"
        ),
        Course(
            imgCategoryCourse = "https://raw.githubusercontent.com/panipujayanti/FinalProjectAsset/master/app/src/main/res/drawable/ic_pm.png",
            nameCourse = "Product Management"
        ),
        Course(
            imgCategoryCourse = "https://raw.githubusercontent.com/panipujayanti/FinalProjectAsset/master/app/src/main/res/drawable/ic_web_development.png",
            nameCourse = "Web Development"
        ),
        Course(
            imgCategoryCourse = "https://raw.githubusercontent.com/panipujayanti/FinalProjectAsset/master/app/src/main/res/drawable/ic_android_development.png",
            nameCourse = "Android Development"
        )
    )

}