package com.raveendra.finalproject_binar.data.network.api.repository

import com.raveendra.finalproject_binar.data.network.api.datasource.CourseDataSource
import com.raveendra.finalproject_binar.data.request.ChangePasswordRequest
import com.raveendra.finalproject_binar.data.request.ForgotPasswordRequest
import com.raveendra.finalproject_binar.data.request.LoginRequest
import com.raveendra.finalproject_binar.data.request.NewOtpRequest
import com.raveendra.finalproject_binar.data.request.RegisterRequest
import com.raveendra.finalproject_binar.data.request.ResetPasswordRequest
import com.raveendra.finalproject_binar.data.request.VerifyOtpRequest
import com.raveendra.finalproject_binar.data.response.BaseResponse
import com.raveendra.finalproject_binar.data.response.historypayment.toDomain
import com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew.toDomain
import com.raveendra.finalproject_binar.data.response.toDomain
import com.raveendra.finalproject_binar.domain.CategoryDomain
import com.raveendra.finalproject_binar.domain.ClassDomain
import com.raveendra.finalproject_binar.domain.CourseDomain
import com.raveendra.finalproject_binar.domain.DetailResponseCourseDomain
import com.raveendra.finalproject_binar.domain.HistoryPaymentDomain
import com.raveendra.finalproject_binar.domain.LoginDomain
import com.raveendra.finalproject_binar.domain.NewOtpDomain
import com.raveendra.finalproject_binar.domain.ProfileDomain
import com.raveendra.finalproject_binar.domain.RegisterDomain
import com.raveendra.finalproject_binar.domain.TransactionDomain
import com.raveendra.finalproject_binar.domain.UpdateClassProgressDomain
import com.raveendra.finalproject_binar.utils.ResultWrapper
import com.raveendra.finalproject_binar.utils.proceedFlow
import com.raveendrag.finalproject_binar.domain.NotificationResponseDomain
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface CourseRepository {
    suspend fun getCourse(
        category: List<Int?>? = null,
        courseType: String? = null
    ): Flow<ResultWrapper<List<CourseDomain>>>

    suspend fun getClass(status: String?): Flow<ResultWrapper<List<ClassDomain>>>

    suspend fun getCategory(): Flow<ResultWrapper<List<CategoryDomain>>>

    suspend fun postLogin(loginRequest: LoginRequest): Flow<ResultWrapper<LoginDomain>>

    suspend fun postRegister(registerRequest: RegisterRequest): Flow<ResultWrapper<RegisterDomain>>

    suspend fun postRequestOtp(newOtpRequest: NewOtpRequest): Flow<ResultWrapper<NewOtpDomain>>
    suspend fun postVerifyOtp(
        userId: Int,
        verifyOtpRequest: VerifyOtpRequest
    ): Flow<ResultWrapper<LoginDomain>>

    suspend fun getProfile(): Flow<ResultWrapper<ProfileDomain>>

    suspend fun postTransaction(courseId: Int): Flow<ResultWrapper<TransactionDomain>>

    suspend fun getCourseById(
        courseId: Int
    ): Flow<ResultWrapper<DetailResponseCourseDomain>>
    suspend fun getClassById(
        courseId: Int
    ): Flow<ResultWrapper<DetailResponseCourseDomain>>

    suspend fun patchClassUpdateProgress(courseId: Int, contentId: Int): Flow<ResultWrapper<UpdateClassProgressDomain>>

    suspend fun postCreateClass(courseId: Int): Flow<ResultWrapper<Unit>>

    suspend fun getHistoryPayment(): Flow<ResultWrapper<HistoryPaymentDomain>>

    suspend fun forgotPassword(
        email: ForgotPasswordRequest
    ): Flow<ResultWrapper<NewOtpDomain>>

    suspend fun forgotPasswordUserId(
        userId: Int,
        verifyOtpRequest: VerifyOtpRequest
    ): Flow<ResultWrapper<LoginDomain>>

    suspend fun resetPassword(
        userId: Int,
        resetPasswordRequest: ResetPasswordRequest
    ): Flow<ResultWrapper<BaseResponse>>

    suspend fun changePassword(
        userId: Int,
        changePasswordRequest: ChangePasswordRequest
    ): Flow<ResultWrapper<BaseResponse>>

    suspend fun getNotification(): Flow<ResultWrapper<NotificationResponseDomain>>

    suspend fun updateProfile(
        image: MultipartBody.Part,
        userId: Int,
        name: RequestBody,
        phoneNumber: RequestBody
    ): Flow<ResultWrapper<BaseResponse>>

}

class CourseRepositoryImpl(private val dataSource: CourseDataSource) : CourseRepository {

    override suspend fun getCourse(
        category: List<Int?>?,
        courseType: String?
    ): Flow<ResultWrapper<List<CourseDomain>>> {
        return proceedFlow { dataSource.getCourse(category, courseType).data?.toDomain().orEmpty() }
    }

    override suspend fun getClass(status: String?): Flow<ResultWrapper<List<ClassDomain>>> {
        return proceedFlow { dataSource.getClass(status).data?.toDomain().orEmpty() }
    }

    override suspend fun getCategory(): Flow<ResultWrapper<List<CategoryDomain>>> {
        return proceedFlow { dataSource.getCategory().data?.toDomain().orEmpty() }
    }

    override suspend fun postLogin(loginRequest: LoginRequest): Flow<ResultWrapper<LoginDomain>> {
        return proceedFlow {
            dataSource.postLogin(loginRequest).toDomain()
        }
    }

    override suspend fun postRegister(registerRequest: RegisterRequest): Flow<ResultWrapper<RegisterDomain>> {
        return proceedFlow {
            dataSource.postRegister(registerRequest).toDomain()
        }
    }

    override suspend fun postRequestOtp(newOtpRequest: NewOtpRequest): Flow<ResultWrapper<NewOtpDomain>> {
        return proceedFlow {
            dataSource.postRequestOtp(newOtpRequest).toDomain()
        }
    }

    override suspend fun postVerifyOtp(
        userId: Int,
        verifyOtpRequest: VerifyOtpRequest
    ): Flow<ResultWrapper<LoginDomain>> {
        return proceedFlow {
            dataSource.postVerifyOtp(userId, verifyOtpRequest).toDomain()
        }
    }

    override suspend fun getProfile(): Flow<ResultWrapper<ProfileDomain>> {
        return proceedFlow {
            dataSource.getProfile().toDomain()
        }
    }

    override suspend fun postTransaction(courseId: Int): Flow<ResultWrapper<TransactionDomain>> {
        return proceedFlow {
            dataSource.postTransaction(courseId).toDomain()
        }
    }

    override suspend fun getCourseById(courseId: Int): Flow<ResultWrapper<DetailResponseCourseDomain>> {
        return proceedFlow {
            dataSource.getCourseById(courseId).toDomain()
        }
    }
    override suspend fun getClassById(courseId: Int): Flow<ResultWrapper<DetailResponseCourseDomain>> {
        return proceedFlow {
            dataSource.getClassById(courseId).toDomain()
        }
    }
    override suspend fun patchClassUpdateProgress(courseId: Int, contentId: Int): Flow<ResultWrapper<UpdateClassProgressDomain>> {
        return proceedFlow {
            dataSource.patchClassUpdateProgress(courseId,contentId).toDomain()
        }
    }

    override suspend fun postCreateClass(courseId: Int): Flow<ResultWrapper<Unit>> {
        return proceedFlow {
            dataSource.postCreateClass(courseId)
        }
    }

    override suspend fun forgotPassword(email: ForgotPasswordRequest): Flow<ResultWrapper<NewOtpDomain>> {
        return proceedFlow {
            dataSource.forgotPassword(email).toDomain()
        }
    }

    override suspend fun forgotPasswordUserId(
        userId: Int,
        verifyOtpRequest: VerifyOtpRequest
    ): Flow<ResultWrapper<LoginDomain>> {
        return proceedFlow {
            dataSource.forgotPasswordUserId(userId, verifyOtpRequest).toDomain()
        }
    }

    override suspend fun resetPassword(
        userId: Int,
        resetPasswordRequest: ResetPasswordRequest
    ): Flow<ResultWrapper<BaseResponse>> {
        return proceedFlow {
            dataSource.resetPasswordUser(userId, resetPasswordRequest)
        }
    }

    override suspend fun changePassword(
        userId: Int,
        changePasswordRequest: ChangePasswordRequest
    ): Flow<ResultWrapper<BaseResponse>> {
        return proceedFlow {
            dataSource.changePasswordUser(userId,changePasswordRequest)
        }
    }


    override suspend fun getHistoryPayment(): Flow<ResultWrapper<HistoryPaymentDomain>> {
        return proceedFlow {
            dataSource.getHistoryPayment().toDomain()
        }
    }

    override suspend fun getNotification(): Flow<ResultWrapper<NotificationResponseDomain>> {
        return proceedFlow {
            dataSource.getNotification().toDomain()
        }
    }

    override suspend fun updateProfile(
        image: MultipartBody.Part,
        userId: Int,
        name: RequestBody,
        phoneNumber: RequestBody
    ): Flow<ResultWrapper<BaseResponse>> {
        return proceedFlow {
            dataSource.updateProfile(image, userId, name, phoneNumber)
        }
    }
}