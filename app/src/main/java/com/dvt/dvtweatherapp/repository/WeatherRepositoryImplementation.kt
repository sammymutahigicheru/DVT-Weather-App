package com.dvt.network.repository

import com.dvt.network.api.WeatherAPI
import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.models.OneShotForeCastResponse
import com.dvt.network.network.DVTResult
import com.dvt.network.network.apiCall

class WeatherRepositoryImplementation(
    private val weatherApi: WeatherAPI,
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        apiKey: String
    ): DVTResult<CurrentWeatherResponse> = apiCall {
        weatherApi.getCurrentWeather(latitude, longitude, apiKey)
    }

    override suspend fun getWeatherForecast(
        latitude: String,
        longitude: String,
        apiKey: String
    ): DVTResult<OneShotForeCastResponse> = apiCall {
        weatherApi.getForecast(latitude, latitude, apiKey)
    }

}