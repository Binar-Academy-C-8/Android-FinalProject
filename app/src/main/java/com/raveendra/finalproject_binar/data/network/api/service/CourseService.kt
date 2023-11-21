package com.raveendra.finalproject_binar.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.raveendra.finalproject_binar.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface CourseService {

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
