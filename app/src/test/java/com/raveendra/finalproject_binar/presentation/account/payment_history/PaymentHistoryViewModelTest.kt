package com.raveendra.finalproject_binar.presentation.account.payment_history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.HistoryPaymentDomain
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
 * hrahm,28/12/2023, 22:12
 */
class PaymentHistoryViewModelTest {
    @MockK
    private lateinit var repo: CourseRepository
    private lateinit var viewModel: PaymentHistoryViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            PaymentHistoryViewModel(repo),
            recordPrivateCalls = true
        )
    }
    @Test
    fun `payment history result  success`() {
        runTest {
            val getHistoryPayment = flow {
                emit(ResultWrapper.Success((mockk<HistoryPaymentDomain> (relaxed = true))))
            }
            coEvery { repo.getHistoryPayment() } returns getHistoryPayment
        }
        viewModel.getHistoryPayment()
        coVerify { repo.getHistoryPayment()}
    }

}