package com.raveendra.finalproject_binar.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.utils.ResultWrapper
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.request.LoginRequest
import com.raveendra.finalproject_binar.domain.LoginDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: CourseRepository) : ViewModel() {
    private val _loginResult = MutableSharedFlow<ResultWrapper<LoginDomain>>()
    val loginResult: MutableSharedFlow<ResultWrapper<LoginDomain>>
        get() = _loginResult

    fun postLogin(loginRequest: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postLogin(loginRequest).collect {
                _loginResult.emit(it)
            }
        }
    }
}