package com.raveendra.finalproject_binar.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.ProfileDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(private val repository: CourseRepository) : ViewModel() {

    private val _resultProfile = MutableLiveData<ResultWrapper<ProfileDomain>>()
    val resultProfile: LiveData<ResultWrapper<ProfileDomain>>
        get() = _resultProfile

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProfile().collect {
                _resultProfile.postValue(it)
            }
        }
    }
}