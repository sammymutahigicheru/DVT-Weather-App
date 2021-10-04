package com.dvt.dvtweatherapp.favourites

import com.dvt.data.database.entities.Favourites

internal object SampleFavoriteData {
    val favourite = Favourites(
        id = 1,
        latitude = -1.5,
        longitude = 36.1,
        isFavourite = 1,
        currentTemperature = 23.4,
        maximumTemperature = 30.1,
        minimumTemperature = 19.4,
        description = "cloudy day",
        locationName = "Nairobi County"
    )
}