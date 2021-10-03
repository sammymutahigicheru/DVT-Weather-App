package com.dvt.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvt.data.database.dao.CurrentWeatherDao
import com.dvt.data.database.dao.FavouritesDao
import com.dvt.data.database.dao.ForecastDao
import com.dvt.data.database.entities.CurrentWeather
import com.dvt.data.database.entities.Favourites
import com.dvt.data.database.entities.ForecastWeather

@Database(
    entities = [
        CurrentWeather::class,
        ForecastWeather::class,
        Favourites::class
    ], version = 5, exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherForecastDao(): ForecastDao
    abstract fun favouritesDao():FavouritesDao
}