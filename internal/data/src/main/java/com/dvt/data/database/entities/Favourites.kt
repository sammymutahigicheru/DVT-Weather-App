package com.dvt.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favourites(
    @PrimaryKey(autoGenerate = false)val id: Int,
    val latitude: Double,
    val longitude:Double,
    val isFavourite:Int = 0,
    val currentTemperature: Double,
    val maximumTemperature: Double,
    val minimumTemperature: Double,
    val description:String,
    val locationName:String,
)