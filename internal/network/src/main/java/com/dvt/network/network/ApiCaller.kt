package com.dvt.network.network

import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> apiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): DTVResult<T> = withContext(dispatcher) {
    try {
        DTVResult.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is SocketTimeoutException -> DTVResult.ServerError(1,ErrorResponse("Socket Time Out","Failure"))
            is IOException -> DTVResult.DTVError
            is HttpException -> {
                val code = throwable.code()

                val errorResponse = convertErrorBody(throwable)
                DTVResult.ServerError(code, errorResponse)
            }
            else -> {
                DTVResult.ServerError(null, null)
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? = try {
    throwable.response()?.errorBody()?.charStream()?.let {
        val gson = GsonBuilder()
            .create()
        gson.fromJson(it, ErrorResponse::class.java)
    }
} catch (exception: Exception) {
    null
}