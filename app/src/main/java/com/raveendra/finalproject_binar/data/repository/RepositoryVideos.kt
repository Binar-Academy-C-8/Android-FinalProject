package com.raveendra.finalproject_binar.data.repository

import com.raveendra.finalproject_binar.data.local.LocalDataSource
import com.raveendra.finalproject_binar.data.network.api.service.dummydatavideos.SectionedData
import com.raveendra.finalproject_binar.utils.ResultWrapper
import com.raveendra.finalproject_binar.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

/**
 *hrahm,26/11/2023, 05:47
 **/
interface RepositoryVideos {
    fun getVideos(): Flow<ResultWrapper<List<SectionedData>>>
    fun getVideoUrl(videoTitle: String): Flow<ResultWrapper<String>>
}

class RepositoryVideosImpl(
    private val localDataSource: LocalDataSource
) : RepositoryVideos {
    override fun getVideos(): Flow<ResultWrapper<List<SectionedData>>> {
        return proceedFlow {
            localDataSource.getVideos()
        }.onStart {
            delay(2000)
        }
    }

    override fun getVideoUrl(videoTitle: String): Flow<ResultWrapper<String>> {
        return proceedFlow {
            localDataSource.getVideoUrl(videoTitle)!!
        }
    }
}