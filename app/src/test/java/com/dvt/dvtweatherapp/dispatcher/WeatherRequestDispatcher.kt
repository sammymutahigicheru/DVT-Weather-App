package com.dvt.dvtweatherapp.dispatcher

import com.dvt.dvtweatherapp.commons.Constants
import com.google.common.io.Resources
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.File
import java.net.HttpURLConnection

/**
 * Handles Requests from mock web server
 */
internal class WeatherRequestDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/weather?${Constants.CURRENT_WEATHER_PARAMS}" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/current_weather.json"))
            }
            "/onecall?${Constants.WEATHER_FORECAST_PARAMS}" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(
                        getJson("json/weather_forecast.json")
                    )
            }
            else -> throw IllegalArgumentException("Unknown Request Path ${request.path.toString()}")
        }
    }

    fun getJson(path: String): String {
        val uri = Resources.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

}