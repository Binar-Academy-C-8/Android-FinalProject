package com.raveendra.finalproject_binar.presentation.auth.login


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.request.LoginRequest
import com.raveendra.finalproject_binar.domain.LoginDomain
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LoginViewModelTest {

    @MockK
    lateinit var repository: CourseRepository

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            LoginViewModel(repository),
            recordPrivateCalls = true
        )
        val registerResultMock = flow {
            emit(ResultWrapper.Success((mockk<LoginDomain>(relaxed = true))))
        }
        coEvery { repository.postLogin(any()) } returns registerResultMock
    }


    @Test
    fun postLogin() {
        viewModel.postLogin(loginRequest = LoginRequest(email = "pani@gmail.com", password = "123")
        )
        coVerify { repository.postLogin(any()) }
    }

}