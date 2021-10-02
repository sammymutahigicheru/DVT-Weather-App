package com.dvt.dvtweatherapp.viewmodel

import com.dvt.network.network.DVTResult
import com.dvt.network.repository.WeatherRepository
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

@ExperimentalCoroutinesApi
internal class WeatherViewModelTest : Spek({

    val weatherRepository = mockk<WeatherRepository>()
    lateinit var weatherViewModel: WeatherViewModel

    val dispatcher = TestCoroutineDispatcher()

    beforeGroup {
        Dispatchers.setMain(dispatcher = dispatcher)
    }

    group("Fetching Current Weather Information") {

        beforeEachTest {
            weatherViewModel =
                WeatherViewModel(weatherRepository = weatherRepository)
        }

        test("Assert that an event was received and return it") {

            runBlocking {
                coEvery { weatherRepository.getCurrentWeather("","","") } returns DVTResult.Success(
                    data = testWeatherResponse
                )
                weatherViewModel.getCurrentWeather()
                coVerify { weatherRepository.getCurrentWeather("","","") }
                weatherViewModel.weatherState.test {
                    awaitEvent()
                }
            }
        }

        test("Test that weather information is fetched successfully") {

            runBlocking {
                coEvery { weatherRepository.getCurrentWeather("","","") } returns DVTResult.Success(
                    data = testWeatherResponse
                )
                weatherViewModel.getCurrentWeather()
                coVerify { weatherRepository.getCurrentWeather("","","") }
                weatherViewModel.weatherState.test {
                    Assertions.assertEquals(awaitItem(), testWeatherResponseResult)
                }
            }
        }
    }

    afterGroup {
        Dispatchers.resetMain()
    }
})