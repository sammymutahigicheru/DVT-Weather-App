package com.dvt.network.repository

import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.models.OneShotForeCastResponse
import com.dvt.network.network.DVTResult

interface WeatherRepository {

    suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        apiKey: String,
    ): DVTResult<CurrentWeatherResponse>

    suspend fun getWeatherForecast(
        latitude: String,
        longitude: String,
        apiKey: String,
    ):DVTResult<OneShotForeCastResponse>
}