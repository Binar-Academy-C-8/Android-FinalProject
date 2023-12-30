package com.raveendra.finalproject_binar.data.network.api.datasource

import com.raveendra.finalproject_binar.data.network.api.service.CourseService
import com.raveendra.finalproject_binar.data.request.ChangePasswordRequest
import com.raveendra.finalproject_binar.data.request.ForgotPasswordRequest
import com.raveendra.finalproject_binar.data.request.LoginRequest
import com.raveendra.finalproject_binar.data.request.NewOtpRequest
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.data.request.ResetPasswordRequest
import com.raveendra.finalproject_binar.data.request.VerifyOtpRequest
import com.raveendra.finalproject_binar.data.response.BaseResponse
import com.raveendra.finalproject_binar.data.response.CategoryResponse
import com.raveendra.finalproject_binar.data.response.ClassResponse
import com.raveendra.finalproject_binar.data.response.CourseResponse
import com.raveendra.finalproject_binar.data.response.LoginResponse
import com.raveendra.finalproject_binar.data.response.NewOtpResponse
import com.raveendra.finalproject_binar.data.response.NotificationResponse
import com.raveendra.finalproject_binar.data.response.ProfileResponse
import com.raveendra.finalproject_binar.data.response.RegisterResponse
import com.raveendra.finalproject_binar.data.response.TransactionResponse
import com.raveendra.finalproject_binar.data.response.UpdateClassProgressResponse
import com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew.CourseApiResponseNew
import com.raveendra.finalproject_binar.data.response.historypayment.HistoryPaymentResponse
import com.raveendra.finalproject_binar.utils.ResponseListWrapper

interface CourseDataSource {

    suspend fun getCourse(category: List<Int?>?, courseType : String?): ResponseListWrapper<CourseResponse>
    suspend fun getClass(status : String?): ResponseListWrapper<ClassResponse>
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

    suspend fun getClassById(
        courseId: Int
    ): CourseApiResponseNew

    suspend fun patchClassUpdateProgress(
        courseId: Int,
        contentId: Int
    ): UpdateClassProgressResponse

    suspend fun postCreateClass(
        courseId: Int
    ): Unit

    suspend fun forgotPassword(
        email: ForgotPasswordRequest
    ): NewOtpResponse
    suspend fun getNotification(): NotificationResponse


    suspend fun forgotPasswordUserId(
        userId: Int,
        verifyOtpRequest: VerifyOtpRequest
    ): LoginResponse

    suspend fun resetPasswordUser(
        userId: Int,
        resetPasswordRequest: ResetPasswordRequest
    ): BaseResponse

    suspend fun changePasswordUser(
        userId: Int,
        changePasswordRequest: ChangePasswordRequest
    ): BaseResponse
}

class CourseDataSourceImpl(
    private val service: CourseService
) : CourseDataSource {

    override suspend fun getCourse(category: List<Int?>?, courseType: String?): ResponseListWrapper<CourseResponse> {
        var listCategory = category ?: emptyList()
        if (listCategory == listOf(null)) listCategory = emptyList()
        val queryParams = mapOf("category" to listCategory)
        return service.getCourse(category = queryParams,courseType = courseType)
    }

    override suspend fun getClass(status : String?): ResponseListWrapper<ClassResponse> {
        return service.getClass(status)
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
    override suspend fun getClassById(courseId: Int): CourseApiResponseNew {
        return service.getClassById(courseId)
    }
    override suspend fun patchClassUpdateProgress(courseId: Int, contentId: Int): UpdateClassProgressResponse {
        return service.patchClassUpdateProgress(courseId,contentId)
    }
    override suspend fun postCreateClass(courseId: Int): Unit {
        return service.postCreateClass(courseId)
    }

    override suspend fun getNotification(): NotificationResponse {
        return service.getNotification()
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

    override suspend fun changePasswordUser(
        userId: Int,
        changePasswordRequest: ChangePasswordRequest
    ): BaseResponse {
        return service.changePassword(userId, changePasswordRequest)
    }
}
