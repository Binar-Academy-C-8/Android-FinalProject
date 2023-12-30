package com.raveendra.finalproject_binar.presentation.auth.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.domain.RegisterDomain
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
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RegisterViewModelTest {
    @MockK
    lateinit var repository: CourseRepository

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            RegisterViewModel(repository),
            recordPrivateCalls = true
        )
        val registerResultMock = flow {
            emit(ResultWrapper.Success((mockk<RegisterDomain> (relaxed = true))))
        }
        coEvery { repository.postRegister(any()) } returns registerResultMock
    }

    @Test
    fun postRegister() {
      viewModel.postRegister(registerRequest = RegisterRequest(email = "pani@gmail.com",
          name ="pani", password = "123", phoneNumber = "094473849"))
        coVerify { repository.postRegister(any()) }
    }
}