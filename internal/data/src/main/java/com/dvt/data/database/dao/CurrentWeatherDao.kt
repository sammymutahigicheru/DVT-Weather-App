package com.dvt.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dvt.data.database.entities.CurrentWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao : BaseDao<CurrentWeather> {

    @Query("SELECT * FROM currentweather")
    fun fetchCurrentWeather(): Flow<CurrentWeather>

    @Query("SELECT * FROM currentweather WHERE isFavourite = 1")
    fun fetchFavorites(): Flow<List<CurrentWeather>>
}