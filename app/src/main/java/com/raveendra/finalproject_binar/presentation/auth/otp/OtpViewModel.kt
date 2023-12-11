package com.raveendra.finalproject_binar.presentation.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.utils.ResultWrapper
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.request.NewOtpRequest
import com.raveendra.finalproject_binar.data.request.VerifyOtpRequest
import com.raveendra.finalproject_binar.domain.NewOtpDomain
import com.raveendra.finalproject_binar.domain.StatusMessageDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class OtpViewModel(private val repository: CourseRepository) : ViewModel() {
    private val _requestOtpResult = MutableSharedFlow<ResultWrapper<NewOtpDomain>>()
    val requestOtpResult: MutableSharedFlow<ResultWrapper<NewOtpDomain>>
        get() = _requestOtpResult

    private val _verifyOtpResult = MutableSharedFlow<ResultWrapper<StatusMessageDomain>>()
    val verifyOtpResult: MutableSharedFlow<ResultWrapper<StatusMessageDomain>>
        get() = _verifyOtpResult


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