package com.dvt.dvtweatherapp.ui.weather.repository

import com.dvt.data.database.dao.CurrentWeatherDao
import com.dvt.data.database.dao.FavouritesDao
import com.dvt.data.database.dao.ForecastDao
import com.dvt.data.database.entities.CurrentWeather
import com.dvt.data.database.entities.Favourites
import com.dvt.data.database.entities.ForecastWeather
import com.dvt.network.api.WeatherAPI
import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.models.OneShotForeCastResponse
import com.dvt.network.network.DVTResult
import com.dvt.network.network.apiCall
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryImplementation(
    private val weatherApi: WeatherAPI,
    private val currentWeatherDao: CurrentWeatherDao?,
    private val weatherForecastDao: ForecastDao?,
    private val favouritesDao: FavouritesDao?
) : WeatherRepository {
    override suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        apiKey: String
    ): DVTResult<CurrentWeatherResponse> {

        return apiCall {
            weatherApi.getCurrentWeather(latitude, longitude, apiKey)
        }
    }

    override suspend fun getWeatherForecast(
        latitude: String,
        longitude: String,
        apiKey: String
    ): DVTResult<OneShotForeCastResponse> = apiCall {
        weatherApi.getForecast(latitude, latitude, apiKey)
    }

    override suspend fun fetchOfflineCurrentWeather(): Flow<CurrentWeather> {
        return currentWeatherDao?.fetchCurrentWeather()!!
    }

    override suspend fun fetchOfflineCurrentForecastWeather(): Flow<List<ForecastWeather>> {
        return weatherForecastDao!!.fetchWeatherForecasts()
    }

    override suspend fun saveCurrentWeather(weather: CurrentWeather) {
        currentWeatherDao!!.insert(weather)
    }

    override suspend fun saveWeatherForecast(weather: List<ForecastWeather>) {
        weatherForecastDao!!.insert(weather)
    }

    override suspend fun saveFavourite(favourites: Favourites) {
        favouritesDao!!.insert(favourites)
    }

}