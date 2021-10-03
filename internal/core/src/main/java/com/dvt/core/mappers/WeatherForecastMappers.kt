package com.dvt.core.mappers

import com.dvt.core.helpers.convertToDay
import com.dvt.data.database.entities.ForecastWeather
import com.dvt.network.models.OneShotForeCastResponse

fun OneShotForeCastResponse.toWeatherForecast(): List<ForecastWeather> {
    val forecasts: MutableList<ForecastWeather> = ArrayList()

    daily.forEach { daily ->
        val foreCastEntity = ForecastWeather(
            day = convertToDay(daily.dt.toLong()),
            id = daily.weather[0].id,
            latitude = lat,
            longitude = lon,
            temperature = daily.temp.day.toInt()
        )

        forecasts.add(foreCastEntity)
    }

    return forecasts
}