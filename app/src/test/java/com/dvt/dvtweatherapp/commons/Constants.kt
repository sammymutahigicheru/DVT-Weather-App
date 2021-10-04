package com.dvt.dvtweatherapp.commons

object Constants {
    const val LATITUDE = "-1.208571"
    const val LONGITUDE = "36.900341"
    const val API_KEY = ""
    const val CURRENT_WEATHER_PARAMS = "lat=$LATITUDE&lon=$LONGITUDE&appid=$API_KEY&units=metric"
    const val WEATHER_FORECAST_PARAMS = "lat=$LATITUDE&lon=$LONGITUDE&appid=$API_KEY&units=metric&exclude=current,minutely,hourly,alerts"
}