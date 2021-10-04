package com.dvt.dvtweatherapp.ui.weather.viewmodel

import app.cash.turbine.test
import com.dvt.dvtweatherapp.commons.Constants
import com.dvt.dvtweatherapp.helpers.testCurrentWeatherResponseResult
import com.dvt.dvtweatherapp.helpers.testWeatherResponse
import com.dvt.network.network.DVTResult
import com.dvt.dvtweatherapp.ui.weather.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions
import org.spekframework.spek2.Spek
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
internal class WeatherViewModelTest : Spek({

    lateinit var weatherViewModel: WeatherViewModel

    val dispatcher = TestCoroutineDispatcher()

    val weatherRepository = mockk<WeatherRepository>()

    beforeGroup {
        Dispatchers.setMain(dispatcher = dispatcher)
    }

    group("Fetching Current Weather Information") {

        beforeEachTest {
            weatherViewModel =
                WeatherViewModel(weatherRepository = weatherRepository, null)
        }

        test("Assert that an event was received and return it") {

            runBlocking {
                coEvery { weatherRepository.getCurrentWeather(Constants.LATITUDE,Constants.LONGITUDE,Constants.API_KEY) } returns DVTResult.Success(
                    data = testWeatherResponse
                )
                weatherViewModel.getCurrentWeather(Constants.LATITUDE,Constants.LONGITUDE,Constants.API_KEY)
                coVerify { weatherRepository.getCurrentWeather(Constants.LATITUDE,Constants.LONGITUDE,Constants.API_KEY) }
                weatherViewModel.currentWeatherState.test {
                    awaitEvent()
                }
            }
        }

//        test("Test that current weather information is fetched successfully") {
//
//            runBlocking {
//                coEvery { weatherRepository.getCurrentWeather(Constants.LATITUDE,Constants.LONGITUDE,Constants.API_KEY) } returns DVTResult.Success(
//                    data = testWeatherResponse
//                )
//                weatherViewModel.getCurrentWeather(Constants.LATITUDE,Constants.LONGITUDE,Constants.API_KEY)
//                coVerify { weatherRepository.getCurrentWeather(Constants.LATITUDE,Constants.LONGITUDE,Constants.API_KEY) }
//                weatherViewModel.currentWeatherState.test {
//                    Assertions.assertEquals(awaitItem(), testCurrentWeatherResponseResult)
//                }
//            }
//        }
    }

    afterGroup {
        Dispatchers.resetMain()
    }
})