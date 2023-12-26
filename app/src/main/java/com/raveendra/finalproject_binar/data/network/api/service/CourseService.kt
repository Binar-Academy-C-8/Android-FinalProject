package com.raveendra.finalproject_binar.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.raveendra.finalproject_binar.BuildConfig
import com.raveendra.finalproject_binar.data.local.sharedpref.PreferenceManager
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
import com.raveendra.finalproject_binar.data.response.historypayment.HistoryPaymentResponse
import com.raveendra.finalproject_binar.data.response.historypayment.UserTransactionResponse
import com.raveendra.finalproject_binar.domain.UserTransactionDomain
import com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew.CourseApiResponseNew
import com.raveendra.finalproject_binar.utils.ResponseListWrapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface CourseService {
    @GET("course")
    suspend fun getCourse(
        @Query("order_by") orderBy: String? = "asc",
        @Query("sort_by") sortBy: String? = "createdAt",
        @Query("category") category : Int?,
        @Query("type") courseType : String? = null
    ): ResponseListWrapper<CourseResponse>

    @GET("category")
    suspend fun getCategory(): ResponseListWrapper<CategoryResponse>

    @POST("auth/member/login")
    suspend fun postLogin(@Body loginRequest: LoginRequest): LoginResponse

    @POST("auth/member/register")
    suspend fun postRegister(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("auth/new-otp")
    suspend fun postRequestOtp(@Body newOtpRequest: NewOtpRequest): NewOtpResponse

    @POST("auth/verify-otp/{userId}")
    suspend fun postVerifyOtp(
        @Path("userId") userId: Int,
        @Body verifyOtpRequest: VerifyOtpRequest
    ): LoginResponse

    @GET("auth/me")
    suspend fun getProfile(): ProfileResponse

    @POST("transaction/{courseId}")
    suspend fun postTransaction(
        @Path("courseId") courseId: Int,
    ): TransactionResponse

    @GET("transaction/history")
    suspend fun getHistoryPayment(): HistoryPaymentResponse

    @GET("course/{id}")
    suspend fun getCourseById(
        @Path("id") courseId: Int
    ): CourseApiResponseNew

    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body email: ForgotPasswordRequest
    ): NewOtpResponse

    @POST("auth/forgot-password/{userId}")
    suspend fun forgotPasswordUserId(
        @Path("userId") userId: Int,
        @Body verifyOtpRequest: VerifyOtpRequest
    ): LoginResponse

    @PATCH("auth/reset-password/{userId}")
    suspend fun resetPasswordUserId(
        @Path("userId") userId: Int,
        @Body resetPasswordRequest: ResetPasswordRequest
    ): BaseResponse

    companion object {

        @JvmStatic
        operator fun invoke(
            chucker: ChuckerInterceptor,
            preferenceManager: PreferenceManager,
        ): CourseService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${preferenceManager.appToken}")
                        .build()
                    chain.proceed(newRequest)
                }
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(CourseService::class.java)
        }
    }
}
