package com.dvt.network.network

sealed class DTVResult<out R> {

    data class Success<out T>(val data: T) : DTVResult<T>()

    data class ServerError(
        val code: Int? = null,
        val errorBody: ErrorResponse? = null
    ) : DTVResult<Nothing>()

    object DTVError : DTVResult<Nothing>()
}