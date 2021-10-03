package com.dvt.dvtweatherapp.repository

import com.dvt.data.database.entities.CurrentWeather
import com.dvt.data.database.entities.ForecastWeather
import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.models.OneShotForeCastResponse
import com.dvt.network.network.DVTResult
import kotlinx.coroutines.flow.Flow

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
    ): DVTResult<OneShotForeCastResponse>

    suspend fun fetchOfflineCurrentWeather(): Flow<CurrentWeather>
    suspend fun fetchOfflineCurrentForecastWeather(): Flow<List<ForecastWeather>>
    suspend fun saveCurrentWeather(weather: CurrentWeather)
    suspend fun saveWeatherForecast(weather: List<ForecastWeather>)
    suspend fun updateCurrentWeather(weather: CurrentWeather)
}