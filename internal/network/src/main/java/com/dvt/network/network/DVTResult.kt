package com.dvt.network.network

sealed class DVTResult<out R> {

    data class Success<out T>(val data: T) : DVTResult<T>()

    data class ServerError(
        val code: Int? = null,
        val errorBody: ErrorResponse? = null
    ) : DVTResult<Nothing>()

    object DVTError : DVTResult<Nothing>()
}