package com.dvt.core.mappers

import com.dvt.data.database.entities.CurrentWeather
import com.dvt.data.database.entities.Favourites
import com.dvt.network.models.CurrentWeatherResponse

fun CurrentWeatherResponse.toCurrentWeather():CurrentWeather{
    val description = if (this.weather.isNotEmpty()) this.weather[0].description else ""
    return CurrentWeather(
        this.id,
        this.coord.lat,
        this.coord.lon,
        0,
        this.main.temp,
        this.main.tempMax,
        this.main.tempMin,
        description,
        this.locationName
    )
}

fun CurrentWeatherResponse.toFavourites():Favourites{
    return Favourites(
        this.id,
        this.coord.lat,
        this.coord.lon,
        0,
        this.main.temp,
        this.main.tempMax,
        this.main.tempMin,
        this.weather[0].description,
        this.locationName
    )
}