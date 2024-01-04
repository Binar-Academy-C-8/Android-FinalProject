package com.raveendra.finalproject_binar.presentation.course

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raveendra.finalproject_binar.data.network.api.repository.CourseRepository
import com.raveendra.finalproject_binar.tools.MainCoroutineRule
import com.raveendra.finalproject_binar.tools.getOrAwaitValue
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

class CourseViewModelTest {

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: CourseRepository

    private lateinit var viewModel: CourseViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            CourseViewModel(repository),
            recordPrivateCalls = true
        )
    }

    @Test
    fun getCourseWhenCourseTypeNotNull(){
        coEvery { repository.getCourse(any(),any(),any(),any()) } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                    )
                )
            )
        }

        val resultNotNullCategory = viewModel.getCourse("tes", listOf(1),"tes")
        val coursesNotNullCategory = viewModel.course.getOrAwaitValue()

        assertEquals(resultNotNullCategory, Unit)
        assertEquals(coursesNotNullCategory.payload?.size, 3)
        coVerify { repository.getCourse(any(),any(),any(),any()) }
    }

    @Test
    fun getCourseWhenCourseTypeNull() {
        coEvery  { repository.getCourse(any(),any(),any(),any()) } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                    )
                )
            )
        }

        val resultNullCategory = viewModel.getCourse(null, listOf(1),"tes")
        val coursesNullCategory = viewModel.course.getOrAwaitValue()

        assertEquals(resultNullCategory, Unit)
        assertEquals(coursesNullCategory.payload?.size, 3)
        coVerify { repository.getCourse(any(),any(),any(),any()) }
    }
}