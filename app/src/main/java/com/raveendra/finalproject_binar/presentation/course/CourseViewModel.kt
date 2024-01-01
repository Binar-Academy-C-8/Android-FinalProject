package com.raveendra.finalproject_binar.presentation.course


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseViewModel(
    val repository: CourseRepository
) : ViewModel() {

    private val _course = MutableLiveData<ResultWrapper<List<CourseDomain>>>()
    val course: LiveData<ResultWrapper<List<CourseDomain>>>
        get() = _course

    fun getCourse(
        courseType: String? = null,
        category: List<Int?>? = null,
        difficulty: String = ""
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourse(
                courseType = if (courseType == "all") null else courseType,
                category = category,
                difficulty = difficulty
            ).collect {
                _course.postValue(it)
            }
        }
    }

    private val _categories = MutableLiveData<ResultWrapper<List<CategoryDomain>>>()
    val categories: LiveData<ResultWrapper<List<CategoryDomain>>>
        get() = _categories

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategory().collect {
                _categories.postValue(it)
            }
        }
    }

}