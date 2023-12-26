package com.raveendra.finalproject_binar.presentation.auth.resetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.request.ResetPasswordRequest
import com.raveendra.finalproject_binar.data.response.BaseResponse
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 *hrahm,25/12/2023, 21:16
 **/
class ResetPasswordViewModel(
    private val repository: CourseRepository
) : ViewModel() {
    private val _resetPassword = MutableSharedFlow<ResultWrapper<BaseResponse>>()
    val resetPassword: MutableSharedFlow<ResultWrapper<BaseResponse>>
        get() = _resetPassword

    fun resetPassword(userId: Int, resetPasswordRequest: ResetPasswordRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.resetPassword(userId, resetPasswordRequest).collect{
                _resetPassword.emit(it)
            }
        }
    }
}