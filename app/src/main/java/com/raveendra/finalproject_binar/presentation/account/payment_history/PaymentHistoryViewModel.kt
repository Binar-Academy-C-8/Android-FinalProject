package com.raveendra.finalproject_binar.presentation.account.payment_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.HistoryPaymentDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentHistoryViewModel(
    private val repo: CourseRepository

): ViewModel() {

    private val _historyPayment = MutableLiveData<ResultWrapper<HistoryPaymentDomain>>()
    val historyPayment: LiveData<ResultWrapper<HistoryPaymentDomain>>
        get() = _historyPayment

    fun getHistoryPayment(){
        viewModelScope.launch(Dispatchers.IO){
            repo.getHistoryPayment().collect(){
                _historyPayment.postValue(it)
            }
        }
    }

}