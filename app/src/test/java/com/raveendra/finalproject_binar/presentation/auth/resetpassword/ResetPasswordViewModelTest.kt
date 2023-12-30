package com.raveendra.finalproject_binar.presentation.auth.resetpassword

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.request.ResetPasswordRequest
import com.raveendra.finalproject_binar.data.response.BaseResponse
import com.raveendra.finalproject_binar.domain.HistoryPaymentDomain
import com.raveendra.finalproject_binar.presentation.account.payment_history.PaymentHistoryViewModel
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
 * hrahm,28/12/2023, 22:20
 */
class ResetPasswordViewModelTest {
    @MockK
    private lateinit var repo: CourseRepository
    private lateinit var viewModel: ResetPasswordViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            ResetPasswordViewModel(repo),
            recordPrivateCalls = true
        )
    }
    @Test
    fun `reset password result  success`() {
        runTest {
            val resetPassordMock = flow {
                emit(ResultWrapper.Success((mockk<BaseResponse> (relaxed = true))))
            }
            coEvery { repo.resetPassword(any(),any()) } returns resetPassordMock
        }
        viewModel.resetPassword(1, ResetPasswordRequest("12345"))
        coVerify { repo.resetPassword(any(),any())}
    }
}