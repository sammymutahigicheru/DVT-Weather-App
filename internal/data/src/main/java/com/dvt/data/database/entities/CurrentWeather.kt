package com.dvt.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentWeather(
    @PrimaryKey(autoGenerate = false)val id: Int,
    val latitude: Double,
    val longitude:Double,
    val isFavourite:Boolean = false,
    val currentTemperature: Int,
    val highTemperature: Int,
    val lowTemperature: Int,
    val description:String
)