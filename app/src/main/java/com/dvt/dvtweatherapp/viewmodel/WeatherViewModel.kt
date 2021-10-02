package com.dvt.dvtweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.dvtweatherapp.utils.ResponseState
import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.network.DVTResult
import com.dvt.network.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _weatherState: MutableStateFlow<ResponseState> =
        MutableStateFlow(ResponseState.Loading)
    val weatherState = _weatherState.asStateFlow()

    private val _currentWeather: MutableStateFlow<CurrentWeatherResponse?> = MutableStateFlow(null)
    val currentWeather = _currentWeather.asStateFlow()

    fun getCurrentWeather(latitude: String, longitude: String, apiKey: String) =
        viewModelScope.launch {
            when (
                val result = weatherRepository.getCurrentWeather(latitude, longitude, apiKey)
            ) {
                is DVTResult.Success -> {
                    _weatherState.value = ResponseState.Result(result.data)
                }
                is DVTResult.DVTError -> {
                    _weatherState.value =
                        ResponseState.Error("Some error occurred when fetching weather")
                }
                is DVTResult.ServerError -> {
                    _weatherState.value = ResponseState.Error(result.errorBody?.message.toString())
                }
            }
        }

    fun getWeatherForecast(latitude: String, longitude: String, apiKey: String) = flow {
        _isLoading.value = true
        emit(weatherRepository.getWeatherForecast(latitude, longitude, apiKey))
        _isLoading.value = false
    }
}