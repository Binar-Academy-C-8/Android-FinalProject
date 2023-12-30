package com.raveendra.finalproject_binar.data.network.api.datasource

import com.raveendra.finalproject_binar.data.network.api.service.CourseService
import com.raveendra.finalproject_binar.data.request.ForgotPasswordRequest
import com.raveendra.finalproject_binar.data.request.LoginRequest
import com.raveendra.finalproject_binar.data.request.NewOtpRequest
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.data.request.ResetPasswordRequest
import com.raveendra.finalproject_binar.data.request.VerifyOtpRequest
import com.raveendra.finalproject_binar.data.response.BaseResponse
import com.raveendra.finalproject_binar.data.response.CategoryResponse
import com.raveendra.finalproject_binar.data.response.CourseResponse
import com.raveendra.finalproject_binar.data.response.LoginResponse
import com.raveendra.finalproject_binar.data.response.ProfileResponse
import com.raveendra.finalproject_binar.data.response.TransactionResponse
import com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew.CourseApiResponseNew
import com.raveendra.finalproject_binar.data.response.historypayment.HistoryPaymentResponse
import com.raveendra.finalproject_binar.utils.ResponseListWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class CourseDataSourceImplTest {

    @MockK
    lateinit var service : CourseService

    private lateinit var courseDataSource: CourseDataSource


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        courseDataSource = CourseDataSourceImpl(service)
    }

    @Test
    fun getCourse() {
        runTest {
            val mockResponse = mockk<ResponseListWrapper<CourseResponse>>(relaxed = true)
            coEvery { service.getCourse(any(), any(),any(),any()) } returns mockResponse
            val response = courseDataSource.getCourse(listOf(1), "Premium")
            coVerify { service.getCourse(any(), any(),any(),any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCategory() {
        runTest {
            val mockResponse = mockk<ResponseListWrapper<CategoryResponse>>(relaxed = true)
            coEvery { service.getCategory() } returns mockResponse
            val response = courseDataSource.getCategory()
            coVerify { service.getCategory() }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun postLogin() {
        runTest {
            val mockRequest = mockk<LoginRequest>(relaxed = true)
            coEvery { service.postLogin(any()) } returns mockk(relaxed = true)
            courseDataSource.postLogin(mockRequest)
            coVerify { service.postLogin(mockRequest) }
        }
    }

    @Test
    fun postRegister() {
        runTest {
            val mockRequest = mockk<RegisterRequest>(relaxed = true)
            coEvery { service.postRegister(any()) } returns mockk(relaxed = true)
            courseDataSource.postRegister(mockRequest)
            coVerify { service.postRegister(mockRequest) }
        }
    }

    @Test
    fun postRequestOtp() {
        runTest {
            val mockRequest = mockk<NewOtpRequest>(relaxed = true)
            coEvery { service.postRequestOtp(any()) } returns mockk(relaxed = true)
            courseDataSource.postRequestOtp(mockRequest)
            coVerify { service.postRequestOtp(mockRequest) }
        }
    }

    @Test
    fun postVerifyOtp() {
        runTest {
            val mockRequest = mockk<VerifyOtpRequest>(relaxed = true)
            coEvery { service.postVerifyOtp(any(), any()) } returns mockk(relaxed = true)
            courseDataSource.postVerifyOtp(1, mockRequest)
            coVerify { service.postVerifyOtp(any(), any()) }
        }
    }


    @Test
    fun getCourseById() {
        runTest {
            val mockResponse = mockk<CourseApiResponseNew>(relaxed = true)
            coEvery { service.getCourseById(any()) } returns mockResponse
            val response = courseDataSource.getCourseById(1)
            coVerify { service.getCourseById(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getProfile() {
        runTest {
            val mockResponse = mockk<ProfileResponse>(relaxed = true)
            coEvery { service.getProfile() } returns mockResponse
            val response = courseDataSource.getProfile()
            coVerify { service.getProfile() }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun postTransaction() {
        runTest {
            val mockResponse = mockk<TransactionResponse>(relaxed = true)
            coEvery { service.postTransaction(any()) } returns mockResponse
            val response = courseDataSource.postTransaction(1)
            coVerify { service.postTransaction(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getHistoryPayment() {
        runTest {
            val mockResponse = mockk<HistoryPaymentResponse>(relaxed = true)
            coEvery { service.getHistoryPayment() } returns mockResponse
            val response = courseDataSource.getHistoryPayment()
            coVerify { service.getHistoryPayment() }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun forgotPassword() {
        runTest {
            val mockRequest = mockk<ForgotPasswordRequest>(relaxed = true)
            coEvery { service.forgotPassword(any()) } returns mockk(relaxed = true)
            courseDataSource.forgotPassword(mockRequest)
            coVerify { service.forgotPassword(mockRequest) }
        }
    }

    @Test
    fun forgotPasswordUserId() {
        runTest {
            val mockRequest = mockk<VerifyOtpRequest>(relaxed = true)
            coEvery { service.forgotPasswordUserId(any(), any()) } returns mockk(relaxed = true)
            courseDataSource.forgotPasswordUserId(1, mockRequest)
            coVerify { service.forgotPasswordUserId(any(), any()) }
        }
    }

    @Test
    fun resetPasswordUser() {
        runTest {
            val mockRequest = mockk<ResetPasswordRequest>(relaxed = true)
            val mockResponse = mockk<BaseResponse>(relaxed = true)
            coEvery { service.resetPasswordUserId(any(), any()) } returns mockResponse
            val response = courseDataSource.resetPasswordUser(1, mockRequest)
            coVerify { service.resetPasswordUserId(any(), any()) }
            assertEquals(response, mockResponse)
        }
    }
}