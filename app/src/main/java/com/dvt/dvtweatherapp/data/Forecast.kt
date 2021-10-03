package com.dvt.dvtweatherapp.data

data class Forecast(
    val day: String,
    val id:Int,
    val latitude: Double,
    val longitude: Double,
    val temperature: Int
)