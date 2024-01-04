package com.raveendra.finalproject_binar.presentation.account.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.response.BaseResponse
import com.raveendra.finalproject_binar.domain.ProfileDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel(private val repository: CourseRepository) : ViewModel() {
    private val _resultProfile = MutableLiveData<ResultWrapper<ProfileDomain>>()
    val resultProfile: LiveData<ResultWrapper<ProfileDomain>>
        get() = _resultProfile

    private val _updateProfile = MutableLiveData<ResultWrapper<BaseResponse>>()
    val updateProfile: LiveData<ResultWrapper<BaseResponse>>
        get() = _updateProfile
    fun updateProfile(
        image: MultipartBody.Part?,
        userId: Int,
        name: RequestBody,
        phoneNumber: RequestBody,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            if (image != null){
                repository.updateProfile(image, userId, name, phoneNumber).collect{
                    _updateProfile.postValue(it)
                }
            }else{
                repository.updateProfile(userId, name, phoneNumber).collect{
                    _updateProfile.postValue(it)
                }
            }
        }
    }
    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProfile().collect {
                _resultProfile.postValue(it)
            }
        }
    }
}