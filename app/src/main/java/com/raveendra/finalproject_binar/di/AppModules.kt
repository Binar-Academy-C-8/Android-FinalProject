package com.raveendra.finalproject_binar.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import com.raveendra.finalproject_binar.data.network.api.service.CourseService
import org.koin.android.ext.koin.androidContext
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

    }

    private val repositoryModule = module {

    }

    private val viewModelModule = module {

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
