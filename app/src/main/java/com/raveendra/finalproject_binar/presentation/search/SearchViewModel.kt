package com.raveendra.finalproject_binar.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    val repository: CourseRepository
) : ViewModel() {
    private val _course = MutableLiveData<ResultWrapper<List<CourseDomain>>>()
    val course: LiveData<ResultWrapper<List<CourseDomain>>>
        get() = _course

    fun getCourse(
        search: String? = ""
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourse(
                search = search
            ).collect {
                _course.postValue(it)
            }
        }
    }
}