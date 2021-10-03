package com.dvt.data.database.dao

import androidx.room.Dao
import com.dvt.data.database.entities.ForecastWeather

@Dao
interface ForecastDao: BaseDao<ForecastWeather> {
}