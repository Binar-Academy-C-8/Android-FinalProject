package com.raveendra.finalproject_binar.data.network.api.repository

import app.cash.turbine.test
import com.raveendra.finalproject_binar.data.network.api.datasource.CourseDataSource
import com.raveendra.finalproject_binar.data.request.ForgotPasswordRequest
import com.raveendra.finalproject_binar.data.request.LoginRequest
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.data.request.ResetPasswordRequest
import com.raveendra.finalproject_binar.data.request.VerifyOtpRequest
import com.raveendra.finalproject_binar.data.response.BaseResponse
import com.raveendra.finalproject_binar.data.response.CategoryResponse
import com.raveendra.finalproject_binar.data.response.CourseResponse
import com.raveendra.finalproject_binar.data.response.CreatedTransactionDataResponse
import com.raveendra.finalproject_binar.data.response.LoginDataResponse
import com.raveendra.finalproject_binar.data.response.LoginResponse
import com.raveendra.finalproject_binar.data.response.NewOtpDataResponse
import com.raveendra.finalproject_binar.data.response.NewOtpRequestResponse
import com.raveendra.finalproject_binar.data.response.NewOtpResponse
import com.raveendra.finalproject_binar.data.response.ProfileDataResponse
import com.raveendra.finalproject_binar.data.response.ProfileResponse
import com.raveendra.finalproject_binar.data.response.RegisterDataResponse
import com.raveendra.finalproject_binar.data.response.RegisterDataValuesResponse
import com.raveendra.finalproject_binar.data.response.RegisterResponse
import com.raveendra.finalproject_binar.data.response.TransactionResponse
import com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew.ChapterResponse
import com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew.ContentResponse
import com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew.CourseApiResponseNew
import com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew.DetailCourseDataResponse
import com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew.toDomain
import com.raveendra.finalproject_binar.data.response.historypayment.CoursePaymentResponse
import com.raveendra.finalproject_binar.data.response.historypayment.HistoryPaymentResponse
import com.raveendra.finalproject_binar.data.response.historypayment.UserTransactionResponse
import com.raveendra.finalproject_binar.utils.ResponseListWrapper
import com.raveendra.finalproject_binar.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * hrahm,27/12/2023, 18:10
 */
class CourseRepositoryImplTest {
    @MockK
    lateinit var dataSource: CourseDataSource
    private lateinit var repository: CourseRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CourseRepositoryImpl(dataSource)
    }

    @Test
    fun `getCategories, with result success`() {
        val fakeCourseResponse = CourseResponse(
            aboutCourse = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            category = "Programming",
            categoryId = 1,
            courseBy = "John Doe",
            courseCode = "CSE101",
            courseLevel = "Intermediate",
            courseName = "Kotlin Programming",
            coursePrice = 500,
            courseType = "Online",
            createdAt = "2023-01-01",
            durationPerCourseInMinutes = 120,
            id = 1,
            image = "https://example.com/course_image.jpg",
            intendedFor = "Developers",
            modulePerCourse = 10,
            updatedAt = "2023-12-27",
            userId = 123,
            ratingCourse = 4.5
        )
        val fakeResponseListWrapper = ResponseListWrapper(
            status = true,
            data = listOf(fakeCourseResponse)
        )
        runTest {
            coEvery { dataSource.getCourse(any(), any(), any(), any()) } returns fakeResponseListWrapper
            repository.getCourse(listOf(1), "Free").map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                coVerify { dataSource.getCourse(any(), any(), any(), any()) }
            }
        }
    }

    @Test
    fun `get category with result success`() {
        val fakeCategoryResponse = CategoryResponse(
            categoryName = "Programming",
            createdAt = "2023-01-01",
            id = 1,
            image = "https://example.com/category_image.jpg",
            updatedAt = "2023-12-27"
        )
        val fakeResponseListWrapper = ResponseListWrapper(
            status = true,
            data = listOf(fakeCategoryResponse)
        )
        runTest {
            coEvery { dataSource.getCategory() } returns fakeResponseListWrapper
            repository.getCategory().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.size, 1)
                coVerify { dataSource.getCategory() }
            }
        }
    }

    @Test
    fun `post login, result success`() {
        //assert equalt not yet implemented
        val fakeLoginDataResponse = LoginDataResponse(
            token = "dummy_token_value"
        )

        val fakeLoginResponse = LoginResponse(
            message = "Login successful",
            status = "success",
            data = fakeLoginDataResponse
        )
        val fakeLoginRequest = LoginRequest(
            email = "test@example.com",
            password = "dummy_password"
        )

        runTest {
            coEvery { dataSource.postLogin(any()) } returns fakeLoginResponse
            repository.postLogin(fakeLoginRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.postLogin(any()) }
            }
        }
    }

    @Test
    fun `post register, result success`() {
        val fakeRegisterDataValuesResponse = RegisterDataValuesResponse(
            city = "Jakarta",
            country = "Indonesia",
            createdAt = "2023-01-01",
            id = 1,
            image = "https://example.com/profile_image.jpg",
            name = "John Doe",
            phoneNumber = "123456789",
            role = "User",
            updatedAt = "2023-12-27"
        )

        val fakeRegisterDataResponse = RegisterDataResponse(
            dataValues = fakeRegisterDataValuesResponse
        )

        val fakeRegisterResponse = RegisterResponse(
            message = "Registration successful",
            data = fakeRegisterDataResponse,
            status = "success"
        )
        val fakeRegisterRequest = RegisterRequest(
            email = "test@example.com",
            name = "John Doe",
            password = "dummy_password",
            phoneNumber = "123456789"
        )

        runTest {
            coEvery { dataSource.postRegister(any()) } returns fakeRegisterResponse
            repository.postRegister(fakeRegisterRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.postRegister(any()) }
            }
        }
    }

    @Test
    fun `get Profile, result success`() {
        val fakeProfileDataResponse = ProfileDataResponse(
            city = "Jakarta",
            country = "Indonesia",
            email = "test@example.com",
            id = 1,
            image = "https://example.com/profile_image.jpg",
            name = "John Doe",
            phoneNumber = "123456789"
        )

        val fakeProfileResponse = ProfileResponse(
            data = fakeProfileDataResponse,
            status = "success"
        )

        runTest {
            coEvery { dataSource.getProfile() } returns fakeProfileResponse
            repository.getProfile().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getProfile() }
            }
        }
    }

    @Test
    fun `post Transaction, result success`() {
        val createdTransactionData = CreatedTransactionDataResponse(
            courseId = 1,
            courseName = "Kotlin Programming",
            createdAt = "2023-01-01",
            id = 123,
            orderId = 456,
            paymentMethod = "Credit Card",
            paymentStatus = "Paid",
            ppn = 10,
            price = 500,
            totalPrice = 550,
            updatedAt = "2023-12-27",
            userId = 789,
            linkPayment = "https://example.com/payment_link"
        )

        val transactionResponse = TransactionResponse(
            createdTransactionData = createdTransactionData,
            email = "test@example.com",
            status = "Success",
            token = "dummy_token_value"
        )

        runTest {
            coEvery { dataSource.postTransaction(any()) } returns transactionResponse
            repository.postTransaction(1).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.postTransaction(any()) }
            }
        }
    }

    @Test
    fun `getHistoryPayment, result success`() {
        val coursePaymentResponse = CoursePaymentResponse(
            aboutCourse = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            adminId = 1,
            categoryId = 2,
            courseCode = "CSE101",
            courseLevel = "Intermediate",
            courseName = "Kotlin Programming",
            coursePrice = 500,
            courseType = "Online",
            createdAt = "2023-01-01",
            id = 123,
            image = "https://example.com/course_image.jpg",
            intendedFor = "Developers",
            rating = 4.5,
            updatedAt = "2023-12-27"
        )

        val userTransactionResponse = UserTransactionResponse(
            course = coursePaymentResponse,
            courseId = 123,
            createdAt = "2023-01-02",
            id = 456,
            linkPayment = "https://example.com/payment_link",
            orderId = 789,
            paymentMethod = "Credit Card",
            paymentStatus = "Paid",
            ppn = 10,
            price = 500,
            totalPrice = 550,
            updatedAt = "2023-12-28",
            userId = 987
        )

        val historyPaymentResponse = HistoryPaymentResponse(
            status = "Success",
            userTransactions = listOf(userTransactionResponse)
        )

        runTest {
            coEvery { dataSource.getHistoryPayment() } returns historyPaymentResponse
            repository.getHistoryPayment().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals(data.payload?.userTransactions?.size, 1)
                coVerify { dataSource.getHistoryPayment() }
            }
        }
    }

    @Test
    fun `getCourseById, result success`() {
        val content = ContentResponse(
            id = 1,
            contentTitle = "Introduction to Kotlin",
            contentUrl = "https://example.com/introduction_video",
            duration = "10:00",
            status = true,
            chapterId = 1,
            createdAt = "2023-01-01",
            updatedAt = "2023-12-27",
            youtubeId = "abc123"
        )

        val chapter = ChapterResponse(
            id = 1,
            chapterTitle = "Getting Started",
            courseId = 123,
            createdAt = "2023-01-01",
            updatedAt = "2023-12-27",
            contents = listOf(content),
            durationPerChapterInMinutes = 10
        )

        val data = DetailCourseDataResponse(
            id = 123,
            courseCode = "CSE101",
            categoryId = 1,
            userId = 456,
            courseName = "Kotlin Programming",
            image = "https://example.com/course_image.jpg",
            courseType = "Online",
            courseLevel = "Intermediate",
            aboutCourse = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            intendedFor = "Developers",
            coursePrice = 500,
            createdAt = "2023-01-01",
            updatedAt = "2023-12-27",
            category = "Programming",
            courseBy = "John Doe",
            rating = 4.5,
            durationPerCourseInMinutes = 10,
            modulePerCourse = 1,
            chapters = listOf(chapter)
        )

        val courseApiResponseNew = CourseApiResponseNew(
            data = data,
            status = "success"
        )
        runTest {
            coEvery { dataSource.getCourseById(any()) } returns courseApiResponseNew
            repository.getCourseById(1).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                val successData = (data as ResultWrapper.Success).payload

                // Convert the expected data to domain
                val expectedDomainData = courseApiResponseNew.toDomain()

                // Assert that the received data is equal to the expected data
                assertEquals(expectedDomainData, successData)
                coVerify { dataSource.getCourseById(any()) }
            }
        }
    }

    @Test
    fun `forgotPassword, result success`() {
        val newOtpRequestResponse = NewOtpRequestResponse(
            code = "123456",
            createdAt = "2023-01-01T12:00:00",
            expiredAt = 60,
            id = 1,
            updatedAt = "2023-01-01T12:05:00",
            userId = 123
        )
        val newOtpDataResponse = NewOtpDataResponse(
            message = "New OTP request successful",
            newOtpRequest = newOtpRequestResponse
        )

        val newOtpResponse = NewOtpResponse(
            data = newOtpDataResponse,
            status = "success"
        )

        val forgotPasswordRequest = ForgotPasswordRequest(
            email = "test@gmail.com"
        )

        runTest {
            coEvery { dataSource.forgotPassword(any()) } returns newOtpResponse
            repository.forgotPassword(forgotPasswordRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals("success", data.payload?.status)
                coVerify { dataSource.forgotPassword(any()) }
            }
        }
    }

    @Test
    fun `forgotPasswordUserId, result success`() {

        val fakeLoginDataResponse = LoginDataResponse(
            token = "dummy_token_value"
        )

        val fakeLoginResponse = LoginResponse(
            message = "Login successful",
            status = "success",
            data = fakeLoginDataResponse
        )
        val fakeVerifyOtpRequest = VerifyOtpRequest(
            code = "1234"
        )


        runTest {
            coEvery { dataSource.forgotPasswordUserId(any(), any()) } returns fakeLoginResponse
            repository.forgotPasswordUserId(1,fakeVerifyOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals("success", data.payload?.status)
                coVerify { dataSource.forgotPasswordUserId(any(), any()) }
            }
        }
    }
    @Test
    fun `reset password, result success`(){
        val fakeBaseResponse = BaseResponse(
            message = "Fake message",
            status = "success"
        )
        val fakeVerifyOtpRequest = ResetPasswordRequest(
            password = "12345678"
        )
        runTest {
            coEvery { dataSource.resetPasswordUser(any(), any()) } returns fakeBaseResponse
            repository.resetPassword(1,fakeVerifyOtpRequest).map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                assertEquals("success", data.payload?.status)
                coVerify { dataSource.resetPasswordUser(any(), any()) }
            }
        }
    }
}