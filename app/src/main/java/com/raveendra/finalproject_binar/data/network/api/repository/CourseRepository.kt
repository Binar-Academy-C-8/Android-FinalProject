package com.raveendra.finalproject_binar.data.network.api.repository

import com.raveendra.finalproject_binar.data.network.api.datasource.CourseDataSource
import com.raveendra.finalproject_binar.data.request.LoginRequest
import com.raveendra.finalproject_binar.data.request.NewOtpRequest
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.data.request.VerifyOtpRequest
import com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew.toDomain
import com.raveendra.finalproject_binar.data.response.toDomain
import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.domain.DetailResponseCourseDomain
import com.raveendra.finalproject_binar.domain.LoginDomain
import com.raveendra.finalproject_binar.domain.NewOtpDomain
import com.raveendra.finalproject_binar.domain.ProfileDomain
import com.raveendra.finalproject_binar.domain.RegisterDomain
import com.raveendra.finalproject_binar.domain.TransactionDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import com.raveendra.finalproject_binar.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun getCourse(category: Int? = null, courseType: String?= null): Flow<ResultWrapper<List<CourseDomain>>>

    suspend fun getCategory(): Flow<ResultWrapper<List<CategoryDomain>>>

    suspend fun postLogin(loginRequest: LoginRequest): Flow<ResultWrapper<LoginDomain>>

    suspend fun postRegister(registerRequest: RegisterRequest): Flow<ResultWrapper<RegisterDomain>>

    suspend fun postRequestOtp(newOtpRequest: NewOtpRequest): Flow<ResultWrapper<NewOtpDomain>>
    suspend fun postVerifyOtp(
        userId: Int,
        verifyOtpRequest: VerifyOtpRequest
    ): Flow<ResultWrapper<LoginDomain>>

    suspend fun getProfile(): Flow<ResultWrapper<ProfileDomain>>

    suspend fun postTransaction(courseId: Int): Flow<ResultWrapper<TransactionDomain>>

    suspend fun getCourseById(
        courseId: Int
    ): Flow<ResultWrapper<DetailResponseCourseDomain>>
}

class CourseRepositoryImpl(private val dataSource: CourseDataSource) : CourseRepository {

    override suspend fun getCourse(category: Int?, courseType: String?): Flow<ResultWrapper<List<CourseDomain>>> {
        return proceedFlow { dataSource.getCourse(category, courseType).data?.toDomain().orEmpty() }
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
    ): Flow<ResultWrapper<LoginDomain>> {
        return proceedFlow {
            dataSource.postVerifyOtp(userId, verifyOtpRequest).toDomain()
        }
    }

    override suspend fun getProfile(): Flow<ResultWrapper<ProfileDomain>> {
        return proceedFlow {
            dataSource.getProfile().toDomain()
        }
    }

    override suspend fun postTransaction(courseId: Int): Flow<ResultWrapper<TransactionDomain>> {
        return proceedFlow {
            dataSource.postTransaction(courseId).toDomain()
        }
    }

    override suspend fun getCourseById(courseId: Int): Flow<ResultWrapper<DetailResponseCourseDomain>> {
        return proceedFlow {
            dataSource.getCourseById(courseId).toDomain()
        }
    }

}