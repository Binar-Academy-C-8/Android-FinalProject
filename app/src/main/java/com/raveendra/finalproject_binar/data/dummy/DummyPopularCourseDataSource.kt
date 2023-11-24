package com.raveendra.finalproject_binar.data.dummy

import com.raveendra.finalproject_binar.model.PopularCourse


interface DummyPopularCourseDataSource {
    fun getPopularCourse(): List<PopularCourse>
}

class DummyPopularCourseDataSourceImpl(): DummyPopularCourseDataSource{
    override fun getPopularCourse(): List<PopularCourse> = listOf(
        PopularCourse(
            imgPopularCourse = "https://raw.githubusercontent.com/panipujayanti/FinalProjectAsset/master/app/src/main/res/drawable/ic_web_development.png",
            namePopularCourse = "Web Development",
            ratingCourse = "4.9",
            tittleCourse = "Belajar Web Development mudah",
            authorCourse = "by ropi",
            levelCourse = "Adanvanced Level",
            durationCourse = "90 menit",
            moduleCourse = "19 Modul",
            priceCourse = 249000.0
        ),
        PopularCourse(
            imgPopularCourse = "https://raw.githubusercontent.com/panipujayanti/FinalProjectAsset/master/app/src/main/res/drawable/ic_web_development.png",
            namePopularCourse = "Web Development",
            ratingCourse = "4.5",
            tittleCourse = "Memahami Web Development dari dasar",
            authorCourse = "by safi",
            levelCourse = "Beginner Level",
            durationCourse = "60 menit",
            moduleCourse = "14 Modul",
            priceCourse = 259000.0
        ),
        PopularCourse(
            imgPopularCourse = "https://raw.githubusercontent.com/panipujayanti/FinalProjectAsset/master/app/src/main/res/drawable/ic_web_development.png",
            namePopularCourse = "Web Development",
            ratingCourse = "4.9",
            tittleCourse = "Kupas tuntas Web Development",
            authorCourse = "by roji",
            levelCourse = "Adanvanced Level",
            durationCourse = "90 menit",
            moduleCourse = "20 Modul",
            priceCourse = 249000.0
        ),
        PopularCourse(
            imgPopularCourse = "https://raw.githubusercontent.com/panipujayanti/FinalProjectAsset/master/app/src/main/res/drawable/ic_web_development.png",
            namePopularCourse = "Web Development",
            ratingCourse = "4.3",
            tittleCourse = "Web Development bagi pemula",
            authorCourse = "by roji",
            levelCourse = "Adanvanced Level",
            durationCourse = "90 menit",
            moduleCourse = "21 Modul",
            priceCourse = 249000.0
        )
    )

}