package com.dvt.dvtweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import com.dvt.network.repository.WeatherRepository

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
):ViewModel() {

}