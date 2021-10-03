package com.dvt.dvtweatherapp.ui.favourites.repository

import com.dvt.data.database.entities.CurrentWeather
import com.dvt.data.database.entities.Favourites
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    suspend fun fetchFavourites(): Flow<List<Favourites>>
}