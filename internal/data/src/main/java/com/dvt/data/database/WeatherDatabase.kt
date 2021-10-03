package com.dvt.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvt.data.database.dao.CurrentWeatherDao
import com.dvt.data.database.dao.ForecastDao
import com.dvt.data.database.entities.CurrentWeather
import com.dvt.data.database.entities.ForecastWeather

@Database(
    entities = [
        CurrentWeather::class,
        ForecastWeather::class
    ], version = 3, exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherForecastDao(): ForecastDao
}