package com.raveendra.finalproject_binar.presentation.home

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

class HomeViewModelTest{
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: CourseRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            HomeViewModel(repository),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `test get categories`() {
        coEvery{ repository.getCategory() } returns flow {
            emit(
                ResultWrapper.Success(
                    listOf(
                        mockk(relaxed = true),
                        mockk(relaxed = true),
                    )
                )
            )
        }
        val result = viewModel.getCategories()
        assertEquals(result, Unit)
        coVerify{ repository.getCategory() }
    }

    @Test
    fun getCoursesWhenCategoryNotNull(){
        coEvery { repository.getCourse(any()) } returns flow {
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

        val resultNotNullCategory = viewModel.getCourses(listOf(1))
        val coursesNotNullCategory = viewModel.course.getOrAwaitValue()

        assertEquals(resultNotNullCategory, Unit)
        assertEquals(coursesNotNullCategory.payload?.size, 3)
        coVerify { repository.getCourse(listOf(1)) }
    }

    @Test
    fun getCoursesWhenCategoryNull(){
        coEvery  { repository.getCourse(null) } returns flow {
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

        val resultNullCategory = viewModel.getCourses(null)
        val coursesNullCategory = viewModel.course.getOrAwaitValue()

        assertEquals(resultNullCategory, Unit)
        assertEquals(coursesNullCategory.payload?.size, 3)
        coVerify { repository.getCourse(null)}
    }


}

