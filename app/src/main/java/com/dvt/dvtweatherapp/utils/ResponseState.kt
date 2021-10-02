package com.dvt.dvtweatherapp.utils

import com.dvt.network.models.CurrentWeatherResponse

sealed class ResponseState {
    object Loading : ResponseState()
    data class Result(val weather: CurrentWeatherResponse) : ResponseState()
    data class Error(val message: String) : ResponseState()
}