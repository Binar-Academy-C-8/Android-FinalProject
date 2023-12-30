package com.raveendra.finalproject_binar.presentation.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.request.NewOtpRequest
import com.raveendra.finalproject_binar.data.request.VerifyOtpRequest
import com.raveendra.finalproject_binar.domain.LoginDomain
import com.raveendra.finalproject_binar.domain.NewOtpDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class OtpViewModel(private val repository: CourseRepository) : ViewModel() {
    private val _requestOtpResult = MutableSharedFlow<ResultWrapper<NewOtpDomain>>()
    val requestOtpResult: MutableSharedFlow<ResultWrapper<NewOtpDomain>>
        get() = _requestOtpResult

    private val _verifyOtpResult = MutableSharedFlow<ResultWrapper<LoginDomain>>()
    val verifyOtpResult: MutableSharedFlow<ResultWrapper<LoginDomain>>
        get() = _verifyOtpResult
    private val _forgotPasswordUserId = MutableSharedFlow<ResultWrapper<LoginDomain>>()
    val forgotPasswordUserId: MutableSharedFlow<ResultWrapper<LoginDomain>>
        get() = _forgotPasswordUserId

    fun forgotPassword(userId: Int, verifyOtpRequest: VerifyOtpRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.forgotPasswordUserId(userId, verifyOtpRequest).collect {
                _forgotPasswordUserId.emit(it)
            }
        }
    }

    fun postRequestOtp(newOtpRequest: NewOtpRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postRequestOtp(newOtpRequest).collect {
                _requestOtpResult.emit(it)
            }
        }
    }

    fun postVerifyOtp(userId: Int,verifyOtpRequest: VerifyOtpRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postVerifyOtp(userId,verifyOtpRequest).collect {
                _verifyOtpResult.emit(it)
            }
        }
    }

}