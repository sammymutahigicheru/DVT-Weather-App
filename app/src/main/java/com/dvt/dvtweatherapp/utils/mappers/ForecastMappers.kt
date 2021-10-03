package com.dvt.dvtweatherapp.utils.mappers

import com.dvt.core.helpers.convertToDay
import com.dvt.data.database.entities.ForecastWeather
import com.dvt.dvtweatherapp.data.Forecast
import com.dvt.network.models.OneShotForeCastResponse

internal fun OneShotForeCastResponse.toForecast(): List<Forecast> {

    val forecasts: MutableList<Forecast> = ArrayList()

    daily.forEach { daily ->
        val forecast = Forecast(
            day = convertToDay(daily.dt.toLong()),
            id = daily.weather[0].id,
            latitude = lat,
            longitude = lon,
            temperature = daily.temp.day.toInt()
        )

        forecasts.add(forecast)
    }

    return forecasts
}

internal fun List<ForecastWeather>.toForecast(): List<Forecast> {
    val forecasts: MutableList<Forecast> = ArrayList()
    this.forEach {
        val forecast = Forecast(
            day = it.day,
            id = it.id,
            longitude = it.longitude,
            latitude = it.latitude,
            temperature = it.temperature
        )
        forecasts.add(forecast)
    }
    return forecasts
}