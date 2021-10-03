package com.dvt.dvtweatherapp.utils.mappers

import com.dvt.data.database.entities.CurrentWeather
import com.dvt.dvtweatherapp.data.Weather
import com.dvt.network.models.CurrentWeatherResponse


internal fun CurrentWeatherResponse.toWeather(): Weather {
    return Weather(
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

internal fun CurrentWeather.toWeather(): Weather {
    return Weather(
        this.id,
        this.latitude,
        this.longitude,
        this.isFavourite,
        this.currentTemperature,
        this.maximumTemperature,
        this.minimumTemperature,
        this.description
    )
}

internal fun Weather.toCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        this.id,
        this.latitude,
        this.longitude,
        this.isFavourite,
        this.currentTemperature,
        this.maximumTemperature,
        this.minimumTemperature,
        this.description
    )
}