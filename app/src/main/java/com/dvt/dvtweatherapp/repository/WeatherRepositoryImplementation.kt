package com.dvt.dvtweatherapp.repository

import android.content.Context
import com.dvt.core.isOnline
import com.dvt.network.api.WeatherAPI
import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.models.OneShotForeCastResponse
import com.dvt.network.network.DVTResult
import com.dvt.network.network.apiCall
import com.dvt.network.repository.WeatherRepository
import timber.log.Timber

class WeatherRepositoryImplementation(
    private val weatherApi: WeatherAPI,
    private val context: Context
) : WeatherRepository {
    private val isOnline = isOnline(context)
    override suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        apiKey: String
    ): DVTResult<CurrentWeatherResponse> {
        if (isOnline) {
            return apiCall {
                weatherApi.getCurrentWeather(latitude, longitude, apiKey)
            }
        } else {
            Timber.e("***Offline****")
            //fetch from roomdb
            return apiCall {
                weatherApi.getCurrentWeather(latitude, longitude, apiKey)
            }
        }
    }

    override suspend fun getWeatherForecast(
        latitude: String,
        longitude: String,
        apiKey: String
    ): DVTResult<OneShotForeCastResponse> = apiCall {
        weatherApi.getForecast(latitude, latitude, apiKey)
    }


}