package com.raveendra.finalproject_binar.presentation.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.HistoryPaymentDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import com.raveendrag.finalproject_binar.domain.NotificationResponseDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val repo : CourseRepository
): ViewModel() {
    private val _notification = MutableLiveData<ResultWrapper<NotificationResponseDomain>>()
    val notification: LiveData<ResultWrapper<NotificationResponseDomain>>
        get() = _notification

    fun getNotification(){
        viewModelScope.launch(Dispatchers.IO){
            repo.getNotification().collect(){
                _notification.postValue(it)
            }
        }
    }
}