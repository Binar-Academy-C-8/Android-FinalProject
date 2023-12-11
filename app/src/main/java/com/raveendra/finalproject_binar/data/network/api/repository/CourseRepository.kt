package com.raveendra.finalproject_binar.data.network.api.repository

import com.raveendra.finalproject_binar.utils.ResultWrapper
import com.raveendra.finalproject_binar.utils.proceedFlow
import com.raveendra.finalproject_binar.data.network.api.datasource.CourseDataSource
import com.raveendra.finalproject_binar.data.request.LoginRequest
import com.raveendra.finalproject_binar.data.request.NewOtpRequest
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.data.request.VerifyOtpRequest
import com.raveendra.finalproject_binar.data.response.toDomain
import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.domain.LoginDomain
import com.raveendra.finalproject_binar.domain.NewOtpDomain
import com.raveendra.finalproject_binar.domain.RegisterDomain
import com.raveendra.finalproject_binar.domain.StatusMessageDomain
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun getCourse(category: String? = null): Flow<ResultWrapper<List<CourseDomain>>>

    suspend fun getCategory(): Flow<ResultWrapper<List<CategoryDomain>>>

    suspend fun postLogin(loginRequest: LoginRequest): Flow<ResultWrapper<LoginDomain>>

    suspend fun postRegister(registerRequest: RegisterRequest): Flow<ResultWrapper<RegisterDomain>>

    suspend fun postRequestOtp(newOtpRequest: NewOtpRequest): Flow<ResultWrapper<NewOtpDomain>>
    suspend fun postVerifyOtp(
        userId: Int,
        verifyOtpRequest: VerifyOtpRequest
    ): Flow<ResultWrapper<StatusMessageDomain>>
}

class CourseRepositoryImpl(private val dataSource: CourseDataSource) : CourseRepository {

    override suspend fun getCourse(category: String?): Flow<ResultWrapper<List<CourseDomain>>> {
        return proceedFlow { dataSource.getCourse(category).data?.toDomain().orEmpty() }
    }

    override suspend fun getCategory(): Flow<ResultWrapper<List<CategoryDomain>>> {
        return proceedFlow { dataSource.getCategory().data?.toDomain().orEmpty() }
    }

    override suspend fun postLogin(loginRequest: LoginRequest): Flow<ResultWrapper<LoginDomain>> {
        return proceedFlow {
            dataSource.postLogin(loginRequest).toDomain()
        }
    }

    override suspend fun postRegister(registerRequest: RegisterRequest): Flow<ResultWrapper<RegisterDomain>> {
        return proceedFlow {
            dataSource.postRegister(registerRequest).toDomain()
        }
    }

    override suspend fun postRequestOtp(newOtpRequest: NewOtpRequest): Flow<ResultWrapper<NewOtpDomain>> {
        return proceedFlow {
            dataSource.postRequestOtp(newOtpRequest).toDomain()
        }
    }

    override suspend fun postVerifyOtp(
        userId: Int,
        verifyOtpRequest: VerifyOtpRequest
    ): Flow<ResultWrapper<StatusMessageDomain>> {
        return proceedFlow {
            dataSource.postVerifyOtp(userId,verifyOtpRequest).toDomain()
        }
    }

}