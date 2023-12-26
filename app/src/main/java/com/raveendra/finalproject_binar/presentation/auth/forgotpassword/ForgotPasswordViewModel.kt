package com.raveendra.finalproject_binar.presentation.auth.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.request.ForgotPasswordRequest
import com.raveendra.finalproject_binar.domain.NewOtpDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 *hrahm,20/12/2023, 20:50
 **/
class ForgotPasswordViewModel(
    private val repository: CourseRepository
):ViewModel() {
    private val _forgotPassword = MutableSharedFlow<ResultWrapper<NewOtpDomain>>()
    val forgotPassword :MutableSharedFlow<ResultWrapper<NewOtpDomain>>
        get() = _forgotPassword

    fun forgotPassword(email:ForgotPasswordRequest){
        viewModelScope.launch(Dispatchers.IO) {
            repository.forgotPassword(email).collect{
                _forgotPassword.emit(it)
            }
        }
    }
}