package com.raveendra.finalproject_binar.presentation.detailcourse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.CreateClassDomain
import com.raveendra.finalproject_binar.domain.DetailResponseCourseDomain
import com.raveendra.finalproject_binar.domain.UpdateClassProgressDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *hrahm,26/11/2023, 05:50
 **/
class DetailViewModel(
    private val repository: CourseRepository
) : ViewModel() {
    private val _detailCourseData = MutableLiveData<ResultWrapper<DetailResponseCourseDomain>>()
    val detailCourseData: LiveData<ResultWrapper<DetailResponseCourseDomain>>
        get() = _detailCourseData

    private val _detailRefreshCourseData = MutableLiveData<ResultWrapper<DetailResponseCourseDomain>>()
    val detailRefreshCourseData: LiveData<ResultWrapper<DetailResponseCourseDomain>>
        get() = _detailRefreshCourseData

    private val _updateClassProgressResult = MutableLiveData<ResultWrapper<UpdateClassProgressDomain>>()
    val updateClassProgressResult: LiveData<ResultWrapper<UpdateClassProgressDomain>>
        get() = _updateClassProgressResult

    private val _postCreateClassResult = MutableLiveData<ResultWrapper<CreateClassDomain>>()
    val postCreateClassResult: LiveData<ResultWrapper<CreateClassDomain>>
        get() = _postCreateClassResult


    fun getDetailCourse(courseId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            courseId.let { courseId ->
                repository.getCourseById(courseId).collect {
                    _detailCourseData.postValue(it)
                }
            }
        }
    }
    fun getDetailClass(courseId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            courseId.let { courseId ->
                repository.getClassById(courseId).collect {
                    _detailCourseData.postValue(it)
                }
            }
        }
    }

    fun getRefreshDetailClass(courseId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            courseId.let { courseId ->
                repository.getClassById(courseId).collect {
                    _detailRefreshCourseData.postValue(it)
                }
            }
        }
    }

    fun patchClassUpdateProgress(courseId : Int, contentId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            courseId.let { courseId ->
                repository.patchClassUpdateProgress(courseId,contentId).collect {
                    _updateClassProgressResult.postValue(it)
                }
            }
        }
    }
    fun postCreateClass(courseId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            courseId.let { courseId ->
                repository.postCreateClass(courseId).collect {
                    _postCreateClassResult.postValue(it)
                }
            }
        }
    }

    //create empty string to pass url video
    private val _contentUrl = MutableLiveData<String>()
    val contentUrl: LiveData<String> get() = _contentUrl
    fun getContentUrl(videoUrl: String) {
        _contentUrl.postValue(videoUrl)
    }

}