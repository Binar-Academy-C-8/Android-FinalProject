package com.raveendra.finalproject_binar.data.repository


import com.raveendra.finalproject_binar.data.dummy.DummyCourseDataSource
import com.raveendra.finalproject_binar.model.Course

interface CourseRepository {
    fun getCourses(): List<Course>
}

class CourseRepositoryImpl(
    private val courseDataSource: DummyCourseDataSource
) : CourseRepository {

    override fun getCourses(): List<Course> {
        return courseDataSource.getCourseList()
    }
}