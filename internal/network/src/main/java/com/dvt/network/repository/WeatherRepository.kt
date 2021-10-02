package com.dvt.network.repository

import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.models.OneShotForeCastResponse
import com.dvt.network.network.DTVResult
import retrofit2.http.Query

interface WeatherRepository {
    suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        apiKey: String,
    ): DTVResult<CurrentWeatherResponse>
    suspend fun getWeatherForecast(
        latitude: String,
        longitude: String,
        apiKey: String,
    ):DTVResult<OneShotForeCastResponse>
}