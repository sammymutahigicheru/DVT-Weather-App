package com.dvt.network.repository

import com.dvt.network.api.WeatherAPI
import com.dvt.network.dispatcher.WeatherRequestDispatcher
import com.dvt.network.models.CurrentWeatherResponse
import com.google.common.truth.Truth
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class WeatherRepositoryImplTest: Spek({

    // Mock Web Server and Network API
    lateinit var mockWebServer: MockWebServer
    lateinit var okHttpClient: OkHttpClient
    lateinit var loggingInterceptor: HttpLoggingInterceptor
    var weatherAPI: WeatherAPI

    lateinit var weatherRepository: WeatherRepository

    lateinit var result: DTVResult<HashMap<String, CurrentWeatherResponse>>

    fun buildOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    Feature("Fetching Weather Reports from API") {

        beforeEachScenario {

            mockWebServer = MockWebServer()
            mockWebServer.dispatcher = WeatherRequestDispatcher()
            mockWebServer.start()
            loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClient = buildOkhttpClient(loggingInterceptor)

            val moshi = Moshi.Builder().build()

            weatherAPI = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(WeatherAPI::class.java)

            weatherRepository =
                WeatherRepositoryImpl(weatherAPI = weatherAPI, weatherDao = mockk())
        }

        afterEachScenario {
            mockWebServer.shutdown()
        }

        Scenario("Fetching the current weather and weather forecast") {

            Given("Make the actual API call to get the result") {
                runBlocking {
                    result = weatherRepository.fetchCurrentWeather()
                }
            }

            When("We assert that the result we get is an instance of DTVResult") {
                Truth.assertThat(result).isInstanceOf(DTVResult.Success::class.java)
            }

            Then("We check the value of today's weather to check if we get the correct lowTemp value") {
                Truth.assertThat((result as DTVResult.Success).data["today"]?.lowTemp)
                    .isEqualTo(23.36)
            }

            Then("We check the value of tomorrow's weather to check if we get the correct highTemp value") {
                Truth.assertThat((result as DTVResult.Success).data["tomorrow"]?.highTemp)
                    .isEqualTo(24.9)
            }

            Then("We check the value of day after tomorrow weather to check if we get the correct description value") {
                Truth.assertThat((result as DTVResult.Success).data["dayAfterTomorrow"]?.description)
                    .isEqualTo("broken clouds")
            }
        }
    }

})