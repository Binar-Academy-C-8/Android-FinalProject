package com.raveendra.finalproject_binar.presentation.payment.payment_summary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.DetailResponseCourseDomain
import com.raveendra.finalproject_binar.domain.TransactionDomain
import com.raveendra.finalproject_binar.tools.MainCoroutineRule
import com.raveendra.finalproject_binar.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/**
 * hrahm,28/12/2023, 21:26
 */
class PaymentSummaryViewModelTest {
    @MockK
    private lateinit var repo: CourseRepository
    private lateinit var viewModel: PaymentSummaryViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            PaymentSummaryViewModel(repo),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `getcourse result  success`() {
        runTest {
            val getCourseMock = flow {
                emit(ResultWrapper.Success((mockk<DetailResponseCourseDomain> (relaxed = true))))
            }
            coEvery { repo.getCourseById(any()) } returns getCourseMock
        }
        viewModel.getDetailCourse(1)
        coVerify { repo.getCourseById(any())}
    }

    @Test
    fun `post transaction result  success`() {
        runTest {
            val posttransactionMock = flow {
                emit(ResultWrapper.Success((mockk<TransactionDomain> (relaxed = true))))
            }
            coEvery { repo.postTransaction(any()) } returns posttransactionMock
        }
        viewModel.postTransaction(1)
        coVerify { repo.postTransaction(any())}
    }

}