package com.raveendra.finalproject_binar.utils

import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.domain.CoursePaymentDomain
import com.raveendra.finalproject_binar.domain.HistoryPaymentDomain
import com.raveendra.finalproject_binar.domain.UserTransactionDomain

interface ViewHolderBinder<T> {
    fun bind(item: T)

}