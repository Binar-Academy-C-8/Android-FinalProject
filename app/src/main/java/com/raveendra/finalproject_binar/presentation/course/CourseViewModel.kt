package com.raveendra.finalproject_binar.presentation.course


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c8.core.utils.ResultWrapper
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.raveendra.finalproject_binar.domain.CourseDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseViewModel(
    val repository: CourseRepository
) : ViewModel() {

    private val _course = MutableLiveData<ResultWrapper<List<CourseDomain>>>()
    val course:LiveData<ResultWrapper<List<CourseDomain>>>
        get() = _course

    fun getCourse(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourse().collect(){
                _course.postValue(it)
            }
        }
    }

}