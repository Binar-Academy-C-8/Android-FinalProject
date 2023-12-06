package com.raveendra.finalproject_binar.data.network.api.datasource

import com.c8.core.utils.ResponseListWrapper
import com.raveendra.finalproject_binar.data.network.api.service.CourseService
import com.raveendra.finalproject_binar.data.response.CategoryResponse
import com.raveendra.finalproject_binar.data.response.CourseResponse

interface CourseDataSource {

    suspend fun getCourse(category: String?): ResponseListWrapper<CourseResponse>
    suspend fun getCategory(): ResponseListWrapper<CategoryResponse>

}

class CourseDataSourceImpl(
    private val service: CourseService
) : CourseDataSource {

    override suspend fun getCourse(category: String?): ResponseListWrapper<CourseResponse> {
        return service.getCourse()
    }

    override suspend fun getCategory(): ResponseListWrapper<CategoryResponse> {
        return service.getCategory()
    }

}
