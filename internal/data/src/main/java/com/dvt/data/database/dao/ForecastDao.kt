package com.dvt.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dvt.data.database.entities.ForecastWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao: BaseDao<ForecastWeather> {

    @Query("SELECT * FROM forecastweather")
    fun fetchWeatherForecasts():Flow<List<ForecastWeather>>

}