package com.raveendra.finalproject_binar.data.network.api.service

import com.c8.core.utils.ResponseListWrapper
import com.raveendra.finalproject_binar.data.response.CategoryResponse
import com.raveendra.finalproject_binar.data.response.CourseResponse
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.raveendra.finalproject_binar.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface CourseService {

    @GET("course")
    suspend fun getCourse(
        @Query("order_by") orderBy: String? = "asc",
        @Query("sort_by") sortBy: String? = "createdAt"
    ): ResponseListWrapper<CourseResponse>

    @GET("category")
    suspend fun getCategory(): ResponseListWrapper<CategoryResponse>

    companion object {
        @JvmStatic
        operator fun invoke(chucker: ChuckerInterceptor): CourseService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chucker)
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
