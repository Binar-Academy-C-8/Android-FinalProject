package com.raveendra.finalproject_binar.presentation.`class`

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.ClassDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClassViewModel(
    val repository: CourseRepository
) : ViewModel() {
    private val _classResult = MutableLiveData<ResultWrapper<List<ClassDomain>>>()
    val course: LiveData<ResultWrapper<List<ClassDomain>>>
        get() = _classResult

    fun getClass(
        status : String? = null
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getClass(status).collect {
                _classResult.postValue(it)
            }
        }
    }
}