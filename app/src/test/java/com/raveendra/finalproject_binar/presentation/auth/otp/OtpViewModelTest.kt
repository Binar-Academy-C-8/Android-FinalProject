package com.raveendra.finalproject_binar.presentation.auth.otp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.request.NewOtpRequest
import com.raveendra.finalproject_binar.data.request.VerifyOtpRequest
import com.raveendra.finalproject_binar.domain.LoginDomain
import com.raveendra.finalproject_binar.domain.NewOtpDomain
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

class OtpViewModelTest {

    @MockK
    lateinit var repository: CourseRepository

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    private lateinit var viewModel: OtpViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            OtpViewModel(repository),
            recordPrivateCalls = true
        )
    }

    @Test
    fun forgotPassword(){
        runTest {
            val passwordResultMock = flow {
                emit(ResultWrapper.Success(mockk<LoginDomain>(relaxed = true)))
            }
            coEvery { repository.forgotPasswordUserId(any(), any()) } returns passwordResultMock
            viewModel.forgotPassword(1, VerifyOtpRequest("1234"))
            coVerify { repository.forgotPasswordUserId(any(), any()) }
        }
    }

    @Test
    fun postRequestOtp(){
        runTest {
            val otpResultMock = flow {
                emit(ResultWrapper.Success(mockk<NewOtpDomain>(relaxed = true)))
            }
            coEvery { repository.postRequestOtp(any()) }returns otpResultMock
            viewModel.postRequestOtp(newOtpRequest = NewOtpRequest(email = "pani@gmail.com"))
            coVerify { repository.postRequestOtp(any()) }
        }
    }

    @Test
    fun postVerifyOtp(){
        runTest {
            val verifyOtpResultMock = flow {
                emit(ResultWrapper.Success(mockk<LoginDomain>(relaxed = true)))
            }
            coEvery { repository.postVerifyOtp(any(), any()) } returns verifyOtpResultMock
            viewModel.postVerifyOtp(1, VerifyOtpRequest("1234"))
            coVerify { repository.postVerifyOtp(any(), any()) }
        }
    }
}