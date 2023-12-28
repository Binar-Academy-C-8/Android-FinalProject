package com.raveendra.finalproject_binar.presentation.detailcourse

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.domain.DetailResponseCourseDomain
import com.raveendra.finalproject_binar.domain.RegisterDomain
import com.raveendra.finalproject_binar.presentation.auth.register.RegisterViewModel
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

class DetailViewModelTest {

    @MockK
    lateinit var repository: CourseRepository

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            DetailViewModel(repository),
            recordPrivateCalls = true
        )
    }

    @Test
    fun getVideos(){
        runTest {
            val getVideosResultMock = flow {
                emit(ResultWrapper.Success((mockk<DetailResponseCourseDomain> (relaxed = true))))
            }
            coEvery { repository.getCourseById(any()) } returns getVideosResultMock
        }

        viewModel.getVideos(1)
        coVerify { repository.getCourseById(any()) }
    }

    @Test
    fun testGetContentUrl(){
        runTest {
            viewModel.getContentUrl("your_video_url")
            assertEquals("your_video_url", viewModel.contentUrl.value)
        }
    }
}