package com.applydigital.hackernews.data.remote

import java.io.IOException
import retrofit2.HttpException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Success(apiCall())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> NetworkResult.Error("Network Error")
            is HttpException -> {
                val code = throwable.code()
                val errorResponse = throwable.response()?.errorBody()?.toString()
                NetworkResult.Error("Error $code $errorResponse")
            }
            else -> NetworkResult.Error(throwable.localizedMessage ?: "Unknown error occurred")
        }
    }
}
