package com.raveendra.finalproject_binar.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.utils.ResultWrapper
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.raveendra.finalproject_binar.domain.CourseDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CourseRepository) : ViewModel() {

    private val _categories = MutableLiveData<ResultWrapper<List<CategoryDomain>>>()
    val categories: LiveData<ResultWrapper<List<CategoryDomain>>>
        get() = _categories

    private val _course = MutableLiveData<ResultWrapper<List<CourseDomain>>>()
    val course: LiveData<ResultWrapper<List<CourseDomain>>>
        get() = _course

    fun getCourses(category: Int? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourse(if (category == 0) null else category).collect {
                _course.postValue(it)
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategory().collect {
                _categories.postValue(it)
            }
        }
    }


}