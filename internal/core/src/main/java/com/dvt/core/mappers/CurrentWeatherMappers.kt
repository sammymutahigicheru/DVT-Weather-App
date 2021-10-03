package com.dvt.core.mappers

import com.dvt.data.database.entities.CurrentWeather
import com.dvt.network.models.CurrentWeatherResponse

fun CurrentWeatherResponse.toCurrentWeather():CurrentWeather{
    return CurrentWeather(
        this.id,
        this.coord.lat,
        this.coord.lon,
        0,
        this.main.temp,
        this.main.tempMax,
        this.main.tempMin,
        this.weather[0].description
    )
}