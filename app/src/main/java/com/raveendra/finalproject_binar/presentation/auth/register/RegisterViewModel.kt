package com.raveendra.finalproject_binar.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.utils.ResultWrapper
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.domain.RegisterDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: CourseRepository) : ViewModel() {
    private val _registerResult = MutableSharedFlow<ResultWrapper<RegisterDomain>>()
    val registerResult: MutableSharedFlow<ResultWrapper<RegisterDomain>>
        get() = _registerResult

    fun postRegister(registerRequest: RegisterRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postRegister(registerRequest).collect {
                _registerResult.emit(it)
            }
        }
    }
}