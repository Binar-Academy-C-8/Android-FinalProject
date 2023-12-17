package com.raveendra.finalproject_binar.presentation.seeallpopularcourse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeeAllPopularCourseViewModel(private val repository: CourseRepository): ViewModel() {
    private val _course = MutableLiveData<ResultWrapper<List<CourseDomain>>>()
    val course: LiveData<ResultWrapper<List<CourseDomain>>>
        get() = _course

    fun getCourses(category: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourse().collect {
                _course.postValue(it)
            }
        }
    }

}