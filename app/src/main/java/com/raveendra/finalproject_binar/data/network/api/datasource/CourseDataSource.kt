package com.raveendra.finalproject_binar.data.network.api.datasource

import com.raveendra.finalproject_binar.data.network.api.service.CourseService
import com.raveendra.finalproject_binar.data.request.ForgotPasswordRequest
import com.raveendra.finalproject_binar.data.request.LoginRequest
import com.raveendra.finalproject_binar.data.request.NewOtpRequest
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.data.request.ResetPasswordRequest
import com.raveendra.finalproject_binar.data.request.VerifyOtpRequest
import com.raveendra.finalproject_binar.data.response.BaseResponse
import com.raveendra.finalproject_binar.data.response.CategoryResponse
import com.raveendra.finalproject_binar.data.response.CourseResponse
import com.raveendra.finalproject_binar.data.response.LoginResponse
import com.raveendra.finalproject_binar.data.response.NewOtpResponse
import com.raveendra.finalproject_binar.data.response.ProfileResponse
import com.raveendra.finalproject_binar.data.response.RegisterResponse
import com.raveendra.finalproject_binar.data.response.TransactionResponse
import com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew.CourseApiResponseNew
import com.raveendra.finalproject_binar.data.response.historypayment.HistoryPaymentResponse
import com.raveendra.finalproject_binar.utils.ResponseListWrapper

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

    suspend fun getHistoryPayment(): HistoryPaymentResponse

    suspend fun getCourseById(
        courseId: Int
    ): CourseApiResponseNew

    suspend fun forgotPassword(
        email: ForgotPasswordRequest
    ): NewOtpResponse

    suspend fun forgotPasswordUserId(
        userId: Int,
        verifyOtpRequest: VerifyOtpRequest
    ): LoginResponse

    suspend fun resetPasswordUser(
        userId: Int,
        resetPasswordRequest: ResetPasswordRequest
    ): BaseResponse
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

    override suspend fun getCourseById(courseId: Int): CourseApiResponseNew {
        return service.getCourseById(courseId)
    }



    override suspend fun getProfile(): ProfileResponse {
        return service.getProfile()
    }

    override suspend fun postTransaction(courseId: Int): TransactionResponse {
        return service.postTransaction(courseId)
    }

    override suspend fun getHistoryPayment(): HistoryPaymentResponse{
        return service.getHistoryPayment()
    }


    override suspend fun forgotPassword(email: ForgotPasswordRequest): NewOtpResponse {
        return service.forgotPassword(email)
    }

    override suspend fun forgotPasswordUserId(
        userId: Int,
        verifyOtpRequest: VerifyOtpRequest
    ): LoginResponse {
        return service.forgotPasswordUserId(userId, verifyOtpRequest)
    }

    override suspend fun resetPasswordUser(
        userId: Int,
        resetPasswordRequest: ResetPasswordRequest
    ): BaseResponse {
        return service.resetPasswordUserId(
            userId, resetPasswordRequest
        )
    }
}
