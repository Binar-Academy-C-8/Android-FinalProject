package com.raveendra.finalproject_binar.data.repository


import com.raveendra.finalproject_binar.data.dummy.DummyCourseDataSource
import com.raveendra.finalproject_binar.model.Course

interface CourseRepositoryDummy {
    fun getCourses(): List<Course>
}

class CourseRepositoryDummyImpl(
    private val courseDataSource: DummyCourseDataSource
) : CourseRepositoryDummy {

    override fun getCourses(): List<Course> {
        return courseDataSource.getCourseList()
    }
}