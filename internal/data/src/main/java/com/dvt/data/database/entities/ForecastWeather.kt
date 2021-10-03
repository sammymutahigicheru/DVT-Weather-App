package com.dvt.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForecastWeather(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "day")
    val day: String = "",
    val latitude: Double,
    val long: Double,
    val temperature: Int
)