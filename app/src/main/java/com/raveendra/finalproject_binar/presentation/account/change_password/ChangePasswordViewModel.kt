package com.raveendra.finalproject_binar.presentation.account.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.request.ChangePasswordRequest
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.data.response.BaseResponse
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val repository: CourseRepository) : ViewModel() {
    private val _changePassword = MutableSharedFlow<ResultWrapper<BaseResponse>>()
    val changePassword: MutableSharedFlow<ResultWrapper<BaseResponse>>
        get() = _changePassword

    fun changePassword(userId: Int, changePasswordRequest: ChangePasswordRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changePassword(userId, changePasswordRequest).collect {
                _changePassword.emit(it)
            }
        }
    }
}