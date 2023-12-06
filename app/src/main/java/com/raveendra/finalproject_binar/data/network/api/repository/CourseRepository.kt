package com.raveendra.finalproject_binar.data.network.api.repository

import com.c8.core.utils.ResultWrapper
import com.c8.core.utils.proceedFlow
import com.raveendra.finalproject_binar.data.network.api.datasource.CourseDataSource
import com.raveendra.finalproject_binar.data.response.toDomain
import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.raveendra.finalproject_binar.domain.CourseDomain
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun getCourse(category: String? = null): Flow<ResultWrapper<List<CourseDomain>>>

    suspend fun getCategory(): Flow<ResultWrapper<List<CategoryDomain>>>
}

class CourseRepositoryImpl(private val dataSource: CourseDataSource) : CourseRepository {

    override suspend fun getCourse(category: String?): Flow<ResultWrapper<List<CourseDomain>>> {
        return proceedFlow { dataSource.getCourse(category).data?.toDomain().orEmpty() }
    }

    override suspend fun getCategory(): Flow<ResultWrapper<List<CategoryDomain>>> {
        return proceedFlow { dataSource.getCategory().data?.toDomain().orEmpty() }
    }

}