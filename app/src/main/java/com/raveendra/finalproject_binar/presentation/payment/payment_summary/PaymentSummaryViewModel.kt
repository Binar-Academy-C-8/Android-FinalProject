package com.raveendra.finalproject_binar.presentation.payment.payment_summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.DetailResponseCourseDomain
import com.raveendra.finalproject_binar.domain.TransactionDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class PaymentSummaryViewModel(private val repository: CourseRepository) : ViewModel() {

    private val _detailData = MutableLiveData<ResultWrapper<DetailResponseCourseDomain>>()
    val detailData: LiveData<ResultWrapper<DetailResponseCourseDomain>>
        get() = _detailData

    private val _transactionResult = MutableSharedFlow<ResultWrapper<TransactionDomain>>()
    val transactionResult: MutableSharedFlow<ResultWrapper<TransactionDomain>>
        get() = _transactionResult


    fun getDetailCourse(courseId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            courseId.let { courseId ->
                repository.getCourseById(courseId).collect {
                    _detailData.postValue(it)
                }
            }
        }
    }

    fun postTransaction(courseId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postTransaction(courseId).collect {
                _transactionResult.emit(it)
            }
        }
    }


}