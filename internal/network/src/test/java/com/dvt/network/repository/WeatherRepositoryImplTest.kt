package com.dvt.network.repository

import com.dvt.network.api.WeatherAPI
import com.dvt.network.dispatcher.WeatherRequestDispatcher
import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.network.DTVResult
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
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

    lateinit var result: DTVResult<CurrentWeatherResponse>

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

            weatherAPI = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(WeatherAPI::class.java)

            weatherRepository =
                WeatherRepositoryImplementation(weatherApi = weatherAPI)
        }

        afterEachScenario {
            mockWebServer.shutdown()
        }

        //success scenario

        Scenario("Fetching the current weather and weather forecast") {

            Given("Make the actual API call to get the result") {
                runBlocking {
                    result = weatherRepository.getCurrentWeather("-1.208571","36.900341","c1373764270eceaee171213e6b2560c7")
                }
            }

            When("We assert that the result we get is an instance of DTVResult") {
                Truth.assertThat(result).isInstanceOf(DTVResult.Success::class.java)
            }

            Then("We check the value of current weather to check if we get the name of the current location") {
                Truth.assertThat((result as DTVResult.Success).data.name)
                    .isEqualTo("Kiambu")
            }

            Then("We check the value of current weather to check if we get the correct highTemp value") {
                Truth.assertThat((result as DTVResult.Success).data.main.tempMax)
                    .isEqualTo(19.4)
            }

            Then("We check the value of current weather to check if we get the correct description value") {
                Truth.assertThat((result as DTVResult.Success).data.weather[2].description)
                    .isEqualTo("broken clouds")
            }
        }
    }


})