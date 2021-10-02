package com.dvt.dvtweatherapp.helpers

import com.dvt.dvtweatherapp.utils.ResponseState
import com.dvt.network.models.*

internal val testWeatherResponse = CurrentWeatherResponse(
    base = "stations",
    clouds = Clouds(
        all = 75
    ),
    cod = 200,
    coord = Coord(
        lat = -1.2086,
        lon = 36.9003
    ),
    dt = 1633121724,
    id = 192710,
    main = Main(
        temp = 19.4,
        feelsLike = 19.04,
        tempMin = 17.98,
        tempMax = 19.4,
        pressure = 1022,
        humidity = 63
    ),
    name = "Kiambu",
    timezone = 10800,
    visibility = 10000,
    weather = listOf(),
    wind = Wind(
        speed = 4.63,
    )
)

internal val testCurrentWeatherResponseResult = ResponseState.Result(testWeatherResponse)