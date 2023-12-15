package com.raveendra.finalproject_binar.data.network.api.datasource

import com.raveendra.finalproject_binar.utils.ResponseListWrapper
import com.raveendra.finalproject_binar.data.network.api.service.CourseService
import com.raveendra.finalproject_binar.data.request.LoginRequest
import com.raveendra.finalproject_binar.data.request.NewOtpRequest
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.data.request.VerifyOtpRequest
import com.raveendra.finalproject_binar.data.response.CategoryResponse
import com.raveendra.finalproject_binar.data.response.CourseResponse
import com.raveendra.finalproject_binar.data.response.LoginResponse
import com.raveendra.finalproject_binar.data.response.RegisterResponse
import com.raveendra.finalproject_binar.data.response.NewOtpResponse
import com.raveendra.finalproject_binar.data.response.ProfileResponse
import com.raveendra.finalproject_binar.data.response.TransactionResponse

interface CourseDataSource {

    suspend fun getCourse(category: Int?, courseType : String?): ResponseListWrapper<CourseResponse>
    suspend fun getCategory(): ResponseListWrapper<CategoryResponse>
    suspend fun postLogin(loginRequest: LoginRequest): LoginResponse
    suspend fun postRegister(registerRequest: RegisterRequest): RegisterResponse
    suspend fun postRequestOtp(newOtpRequest: NewOtpRequest): NewOtpResponse
    suspend fun postVerifyOtp(
        userId: Int,
        verifyOtpRequest: VerifyOtpRequest
    ): LoginResponse
    suspend fun getProfile(): ProfileResponse
    suspend fun postTransaction(courseId: Int): TransactionResponse
}

class CourseDataSourceImpl(
    private val service: CourseService
) : CourseDataSource {

    override suspend fun getCourse(category: Int?, courseType: String?): ResponseListWrapper<CourseResponse> {
        return service.getCourse(category = category,courseType = courseType)
    }

    override suspend fun getCategory(): ResponseListWrapper<CategoryResponse> {
        return service.getCategory()
    }

    override suspend fun postLogin(loginRequest: LoginRequest): LoginResponse {
        return service.postLogin(loginRequest)
    }

    override suspend fun postRegister(registerRequest: RegisterRequest): RegisterResponse {
        return service.postRegister(registerRequest)
    }

    override suspend fun postRequestOtp(newOtpRequest: NewOtpRequest): NewOtpResponse {
        return service.postRequestOtp(newOtpRequest)
    }

    override suspend fun postVerifyOtp(
        userId: Int,
        verifyOtpRequest: VerifyOtpRequest
    ): LoginResponse {
        return service.postVerifyOtp(userId,verifyOtpRequest)
    }

    override suspend fun getProfile(): ProfileResponse {
        return service.getProfile()
    }

    override suspend fun postTransaction(courseId: Int): TransactionResponse {
        return service.postTransaction(courseId)
    }

}
