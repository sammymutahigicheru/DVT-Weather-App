package com.dvt.network.api

import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.models.OneShotForeCastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"

    ): CurrentWeatherResponse

    @GET("onecall")
    suspend fun getForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "current,minutely,hourly,alerts"
    ): OneShotForeCastResponse
}