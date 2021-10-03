package com.dvt.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForecastWeather(
    @PrimaryKey(autoGenerate = false)
    val day: String = "",
    val id:Int,
    val latitude: Double,
    val longitude: Double,
    val temperature: Int
)