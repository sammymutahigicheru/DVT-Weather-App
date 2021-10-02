package com.dvt.dvtweatherapp.utils

import com.dvt.network.models.CurrentWeatherResponse

sealed class ResponseState {
    object Loading : ResponseState()
    data class Result<T>(val data: T) : ResponseState()
    data class Error(val message: String) : ResponseState()
}