package com.dvt.data


data class OneShotForeCastResponse(
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)