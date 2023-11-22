package com.raveendra.finalproject_binar.data.repository


import com.raveendra.finalproject_binar.data.dummy.CourseDataSource
import com.raveendra.finalproject_binar.model.Course

interface CourseRepository {
    fun getProducts(): List<Course>
}

class CourseRepositoryImpl(
    private val productDataSource: CourseDataSource
) : CourseRepository {

    override fun getProducts(): List<Course> {
        return productDataSource.getCourseList()
    }
}