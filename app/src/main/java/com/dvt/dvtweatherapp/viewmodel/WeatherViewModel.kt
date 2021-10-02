package com.dvt.dvtweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.dvtweatherapp.utils.ResponseState
import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.network.DVTResult
import com.dvt.network.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
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
                    _currentWeatherState.value = ResponseState.Error(result.errorBody?.message.toString())
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
                    _weatherForecastState.value = ResponseState.Error(result.errorBody?.message.toString())
                }
            }
        }
}