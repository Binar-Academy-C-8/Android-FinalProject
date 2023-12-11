package com.raveendra.finalproject_binar.presentation.detailcourse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.utils.ResultWrapper
import com.raveendra.finalproject_binar.data.network.api.service.dummydatavideos.SectionedData
import com.raveendra.finalproject_binar.data.repository.RepositoryVideos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *hrahm,26/11/2023, 05:50
 **/
class DetailViewModel(
    private val repositoryVideos: RepositoryVideos
): ViewModel() {
    private val _listVideos= MutableLiveData<ResultWrapper<List<SectionedData>>>()
    val  listVideos : LiveData<ResultWrapper<List<SectionedData>>>
        get() =  _listVideos
    private val _videoUrl = MutableLiveData<ResultWrapper<String>>()
    val videoUrl: LiveData<ResultWrapper<String>>
        get() = _videoUrl
    fun getvideos(){
        viewModelScope.launch(Dispatchers.IO) {
            repositoryVideos.getVideos().collect{
                _listVideos.postValue(it)
            }
        }
    }

    fun getVideoUrl(videoTitle: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryVideos.getVideoUrl(videoTitle).collect {
                _videoUrl.postValue(it)
            }
        }
    }

}