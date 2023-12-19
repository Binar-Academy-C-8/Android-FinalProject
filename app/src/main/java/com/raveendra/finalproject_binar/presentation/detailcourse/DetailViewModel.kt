package com.raveendra.finalproject_binar.presentation.detailcourse

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.DetailResponseCourseDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *hrahm,26/11/2023, 05:50
 **/
class DetailViewModel(
    private val repository: CourseRepository
) : ViewModel() {

    private val _detailData = MutableLiveData<ResultWrapper<DetailResponseCourseDomain>>()
    val detailData: LiveData<ResultWrapper<DetailResponseCourseDomain>>
        get() = _detailData
    fun getVideos(courseId: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getCourseById(courseId).collect {
                    _detailData.postValue(it)
                }
            }
        } catch (e: Throwable) {
            Log.e("DetailViewModel", "getVideos: Error - ${e.message}", e)

        }
    }
//create empty string to pass url video
    private val _contentUrl = MutableLiveData<String>()
    val contentUrl: LiveData<String> get() = _contentUrl
    fun getContentUrl(videoUrl:String) {
        _contentUrl.postValue(videoUrl)
    }

}