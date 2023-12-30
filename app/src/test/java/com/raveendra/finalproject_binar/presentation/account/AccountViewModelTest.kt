package com.raveendra.finalproject_binar.presentation.account

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.domain.ProfileDataDomain
import com.raveendra.finalproject_binar.domain.ProfileDomain
import com.raveendra.finalproject_binar.tools.MainCoroutineRule
import com.raveendra.finalproject_binar.tools.getOrAwaitValue
import com.raveendra.finalproject_binar.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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

/**
 * hrahm,28/12/2023, 20:24
 */
class AccountViewModelTest {
    @MockK
    private lateinit var repo: CourseRepository
    private lateinit var viewModel: AccountViewModel

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            AccountViewModel(repo),
            recordPrivateCalls = true
        )
        coEvery { repo.getProfile() } returns flow {
            emit(
                ResultWrapper.Success(
                    ProfileDomain(
                        data = ProfileDataDomain(
                            city = "Jakarta",
                            country = "Indonesia",
                            email = "john.doe@example.com",
                            id = 1,
                            image = "https://example.com/image.jpg",
                            name = "John Doe",
                            phoneNumber = "123456789"
                        ),
                        status = "success"
                    )
                )
            )
        }
    }

    @Test
    fun `getProfile result  profil`() {
        viewModel.getProfile()
        val result = viewModel.resultProfile.getOrAwaitValue()
        assertTrue( result is ResultWrapper.Success)
    }

}