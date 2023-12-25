package com.raveendra.finalproject_binar.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.raveendra.finalproject_binar.data.local.LocalDataSource
import com.raveendra.finalproject_binar.data.local.LocalDataSourceimpl
import com.raveendra.finalproject_binar.data.local.sharedpref.PreferenceManager
import com.raveendra.finalproject_binar.data.local.sharedpref.PreferenceManagerImpl
import com.raveendra.finalproject_binar.data.network.api.datasource.CourseDataSource
import com.raveendra.finalproject_binar.data.network.api.datasource.CourseDataSourceImpl
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepositoryImpl
import com.raveendra.finalproject_binar.data.network.api.service.CourseService
import com.raveendra.finalproject_binar.data.repository.RepositoryVideos
import com.raveendra.finalproject_binar.data.repository.RepositoryVideosImpl
import com.raveendra.finalproject_binar.presentation.account.AccountViewModel
import com.raveendra.finalproject_binar.presentation.account.payment_history.PaymentHistoryViewModel
import com.raveendra.finalproject_binar.presentation.course.CourseViewModel
import com.raveendra.finalproject_binar.presentation.account.profile.ProfileViewModel
import com.raveendra.finalproject_binar.presentation.auth.login.LoginViewModel
import com.raveendra.finalproject_binar.presentation.auth.otp.OtpViewModel
import com.raveendra.finalproject_binar.presentation.auth.register.RegisterViewModel
import com.raveendra.finalproject_binar.presentation.detailcourse.DetailViewModel
import com.raveendra.finalproject_binar.presentation.home.HomeViewModel
import com.raveendra.finalproject_binar.presentation.payment.payment_summary.PaymentSummaryViewModel
import com.raveendra.finalproject_binar.presentation.payment.payment_webview.PaymentWebViewViewModel
import com.raveendra.finalproject_binar.presentation.seeallpopularcourse.SeeAllPopularCourseViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {

    private const val MAIN_PREFS_LOCAL = "MAIN_PREFS_LOCAL"
    private fun provideSettingsPreferences(app: Application): SharedPreferences =
        app.getSharedPreferences(MAIN_PREFS_LOCAL, Context.MODE_PRIVATE)

    private val localModule = module {
        single { provideSettingsPreferences(androidApplication()) }
        single<PreferenceManager> { PreferenceManagerImpl(get()) }
    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { CourseService.invoke(get(), get()) }
    }

    private val dataSourceModule = module {
        single<CourseDataSource> { CourseDataSourceImpl(get()) }
        single<LocalDataSource> { LocalDataSourceimpl() }
    }

    private val repositoryModule = module {
        single<CourseRepository> { CourseRepositoryImpl(get()) }
        single<RepositoryVideos> { RepositoryVideosImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::CourseViewModel)
        viewModelOf(::DetailViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::OtpViewModel)
        viewModelOf(::SeeAllPopularCourseViewModel)
        viewModelOf(::PaymentSummaryViewModel)
        viewModelOf(::PaymentWebViewViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::AccountViewModel)
        viewModelOf(::PaymentHistoryViewModel)
    }

    val modules = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule,
    )
}
