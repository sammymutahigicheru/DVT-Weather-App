package com.dvt.network.repository

import com.dvt.network.api.WeatherAPI
import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.models.OneShotForeCastResponse
import com.dvt.network.network.DVTResult
import com.dvt.network.network.apiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class WeatherRepositoryImplementation(
    private val weatherApi: WeatherAPI,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        apiKey: String
    ): DVTResult<CurrentWeatherResponse> = apiCall(ioDispatcher) {
        weatherApi.getCurrentWeather(latitude, longitude, apiKey)
    }

    override suspend fun getWeatherForecast(
        latitude: String,
        longitude: String,
        apiKey: String
    ): DVTResult<OneShotForeCastResponse> = apiCall(ioDispatcher) {
        weatherApi.getForecast(latitude, latitude, apiKey)
    }

}