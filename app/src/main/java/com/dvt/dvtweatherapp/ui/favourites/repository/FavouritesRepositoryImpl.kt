package com.dvt.dvtweatherapp.ui.favourites.repository

import com.dvt.data.database.dao.CurrentWeatherDao
import com.dvt.data.database.dao.FavouritesDao
import com.dvt.data.database.entities.CurrentWeather
import com.dvt.data.database.entities.Favourites
import kotlinx.coroutines.flow.Flow

class FavouritesRepositoryImpl(
    private val favouritesDao: FavouritesDao
): FavouritesRepository {

    override suspend fun fetchFavourites(): Flow<List<Favourites>> {
        return favouritesDao.fetchFavourites()
    }
}