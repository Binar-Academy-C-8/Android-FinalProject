package com.raveendra.finalproject_binar.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import com.raveendra.finalproject_binar.data.local.LocalDataSource
import com.raveendra.finalproject_binar.data.local.LocalDataSourceimpl
import com.raveendra.finalproject_binar.data.network.api.service.CourseService
import com.raveendra.finalproject_binar.data.repository.RepositoryVideos
import com.raveendra.finalproject_binar.data.repository.RepositoryVideosImpl
import com.raveendra.finalproject_binar.presentation.detailcourse.DetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.scope.get
import org.koin.dsl.module

object AppModules {

    private val localModule = module {

    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { CourseService.invoke(get()) }
        single { FirebaseAuth.getInstance() }
    }

    private val dataSourceModule = module {
    single <LocalDataSource>{LocalDataSourceimpl()  }
    }

    private val repositoryModule = module {
        single<RepositoryVideos> { RepositoryVideosImpl(get()) }
    }

    private val viewModelModule = module {
        viewModelOf(::DetailViewModel)
    }
    private val utilsModule = module {

    }

    val modules = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule,
        utilsModule
    )
}
