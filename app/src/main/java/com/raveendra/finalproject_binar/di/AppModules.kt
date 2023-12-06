package com.raveendra.finalproject_binar.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.raveendra.finalproject_binar.data.network.api.datasource.CourseDataSource
import com.raveendra.finalproject_binar.data.network.api.datasource.CourseDataSourceImpl
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepositoryImpl
import com.raveendra.finalproject_binar.data.local.LocalDataSource
import com.raveendra.finalproject_binar.data.local.LocalDataSourceimpl
import com.raveendra.finalproject_binar.data.network.api.service.CourseService
import com.raveendra.finalproject_binar.presentation.home.HomeViewModel
import com.raveendra.finalproject_binar.data.repository.RepositoryVideos
import com.raveendra.finalproject_binar.data.repository.RepositoryVideosImpl
import com.raveendra.finalproject_binar.presentation.detailcourse.DetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {

    private val localModule = module {

    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { CourseService.invoke(get()) }
    }

    private val dataSourceModule = module {
        single<CourseDataSource> { CourseDataSourceImpl(get()) }
    single <LocalDataSource>{LocalDataSourceimpl()  }
    }

    private val repositoryModule = module {
        single<CourseRepository> { CourseRepositoryImpl(get()) }
        single<RepositoryVideos> { RepositoryVideosImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::DetailViewModel)
    }

    val modules = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule
    )
}
