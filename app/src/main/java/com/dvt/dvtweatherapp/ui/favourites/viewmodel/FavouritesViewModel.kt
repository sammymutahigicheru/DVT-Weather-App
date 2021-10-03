package com.dvt.dvtweatherapp.ui.favourites.viewmodel

import androidx.lifecycle.ViewModel
import com.dvt.data.database.entities.CurrentWeather
import com.dvt.data.database.entities.Favourites
import com.dvt.dvtweatherapp.ui.favourites.repository.FavouritesRepository
import kotlinx.coroutines.flow.Flow

class FavouritesViewModel(
    private val favouritesRepository: FavouritesRepository
):ViewModel() {

    suspend fun fetchfavourites(): Flow<List<Favourites>>{
        return favouritesRepository.fetchFavourites()
    }
}