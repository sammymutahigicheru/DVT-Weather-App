package com.dvt.dvtweatherapp.viewmodel

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.dvtweatherapp.utils.ResponseState
import com.dvt.network.network.DVTResult
import com.dvt.network.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val client: FusedLocationProviderClient?
) : ViewModel() {

    private val _currentWeatherState: MutableStateFlow<ResponseState> =
        MutableStateFlow(ResponseState.Loading)
    val currentWeatherState = _currentWeatherState.asStateFlow()


    private val _weatherForecastState: MutableStateFlow<ResponseState> =
        MutableStateFlow(ResponseState.Loading)
    val weatherForecastState = _weatherForecastState.asStateFlow()


    fun getCurrentWeather(latitude: String, longitude: String, apiKey: String) =
        viewModelScope.launch {
            when (
                val result = weatherRepository.getCurrentWeather(latitude, longitude, apiKey)
            ) {
                is DVTResult.Success -> {
                    _currentWeatherState.value = ResponseState.Result(result.data)
                }
                is DVTResult.DVTError -> {
                    _currentWeatherState.value =
                        ResponseState.Error("Some error occurred when fetching weather")
                }
                is DVTResult.ServerError -> {
                    _currentWeatherState.value =
                        ResponseState.Error(result.errorBody?.message.toString())
                }
            }
        }

    fun getWeatherForecast(latitude: String, longitude: String, apiKey: String) =
        viewModelScope.launch {
            when (
                val result = weatherRepository.getWeatherForecast(latitude, longitude, apiKey)
            ) {
                is DVTResult.Success -> {
                    _weatherForecastState.value = ResponseState.Result(result.data)
                }
                is DVTResult.DVTError -> {
                    _weatherForecastState.value =
                        ResponseState.Error("Some error occurred when fetching weather")
                }
                is DVTResult.ServerError -> {
                    _weatherForecastState.value =
                        ResponseState.Error(result.errorBody?.message.toString())
                }
            }
        }

    @ExperimentalCoroutinesApi
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.create().apply {
            interval = UPDATE_INTERVAL
            fastestInterval = FASTEST_UPDATE_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val callBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation
                Timber.e("Current Location $location")
                trySend(location)
            }
        }
        client?.requestLocationUpdates(locationRequest, callBack, Looper.getMainLooper())
        awaitClose { client?.let { it.removeLocationUpdates(callBack) } }
    }

    companion object {
        private const val UPDATE_INTERVAL = 1000L
        private const val FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL
    }

}