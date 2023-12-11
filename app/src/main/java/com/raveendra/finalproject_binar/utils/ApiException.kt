package com.raveendra.finalproject_binar.utils

import com.google.gson.Gson
import com.raveendra.finalproject_binar.data.response.BaseResponse
import retrofit2.Response

class ApiException(
    override val message: String?,
    val httpCode : Int,
    val errorResponse: Response<*>?
) : Exception() {

    fun getParsedError() : BaseResponse? {
        val body = errorResponse?.errorBody()?.string().orEmpty()
        return try {
            val bodyObj = Gson().fromJson(body,BaseResponse::class.java)
            bodyObj
        }catch (e : Exception){
            e.printStackTrace()
            null
        }
    }
}
