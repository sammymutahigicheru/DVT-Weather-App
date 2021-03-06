package com.dvt.dvtweatherapp.data

data class Weather(
    val id: Int,
    val latitude: Double,
    val longitude:Double,
    var isFavourite:Int = 0,
    val currentTemperature: Double,
    val maximumTemperature: Double,
    val minimumTemperature: Double,
    val description:String,
    val locationName:String
)