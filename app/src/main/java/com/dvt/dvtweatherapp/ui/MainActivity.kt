package com.dvt.dvtweatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import com.dvt.dvtweatherapp.BuildConfig
import com.dvt.dvtweatherapp.R
import com.dvt.dvtweatherapp.databinding.ActivityMainBinding
import com.dvt.dvtweatherapp.utils.Constants
import com.dvt.dvtweatherapp.utils.ResponseState
import com.dvt.dvtweatherapp.utils.extensions.isLocationEnabled
import com.dvt.dvtweatherapp.utils.extensions.isLocationPermissionEnabled
import com.dvt.dvtweatherapp.viewmodel.WeatherViewModel
import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.models.OneShotForeCastResponse
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: WeatherViewModel by viewModel()

    private lateinit var progressDialog: MaterialAlertDialogBuilder

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = MaterialAlertDialogBuilder(this).apply {
            setCancelable(false)
        }
        checkForLocationPermission()
    }

    private fun fetchWeatherForecast() {
        lifecycleScope.launchWhenStarted {
            val progress = progressDialog.create()
            viewModel.weatherForecastState.collect { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        progress.setMessage("fetching weather forecast info...")
                        progress.show()
                    }
                    is ResponseState.Result<*> -> {
                        progress.dismiss()
                        val result: OneShotForeCastResponse = state.data as OneShotForeCastResponse
                        setUpRecyclerView(result)
                    }
                    is ResponseState.Error -> {
                        Timber.e("Error: ${state.message}")
                        progress.dismiss()
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView(result: OneShotForeCastResponse) {
        val weeklyForecastAdapter = WeeklyForecastAdapter()
        binding.weeklyForecastRecyclerview.apply {
            adapter = weeklyForecastAdapter
            hasFixedSize()
        }
        weeklyForecastAdapter.submitList(result.daily)
    }

    private fun fetchCurrentWeather() {
        lifecycleScope.launchWhenStarted {
            val progress = progressDialog.create()
            viewModel.currentWeatherState.collect { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        progress.setMessage("fetching current weather info...")
                        progress.show()
                    }
                    is ResponseState.Result<*> -> {
                        progress.dismiss()
                        val currentWeather: CurrentWeatherResponse =
                            state.data as CurrentWeatherResponse
                        setUpView(currentWeather)
                    }
                    is ResponseState.Error -> {
                        Timber.e("Error: ${state.message}")
                        progress.dismiss()
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun setUpView(currentWeather: CurrentWeatherResponse) {
        Timber.e("Current Weather $currentWeather")
        binding.apply {
            temperatureTextview.text = "${currentWeather.main.temp} ℃"
            weatherDescriptionTextview.text = currentWeather.weather[0].description
            minimumTemperatureTexview.text = "${currentWeather.main.tempMin} ℃"
            currentTemperatureTextview.text = "${currentWeather.main.temp} ℃"
            maximumTemperatureTitle.text = "${currentWeather.main.tempMax} ℃"
        }
        when {
            currentWeather.weather[0].description.contains("rain", true) -> {
                binding.apply {
                    rootLayout.setBackgroundColor(R.color.rainy)
                    currentWeatherLayout.setBackgroundResource(R.drawable.forest_rainy)
                }
                val bitMap: Bitmap = getBitmapResource(R.drawable.forest_rainy)
                updateStatusBarColor(bitMap)
            }
            currentWeather.weather[0].description.contains("sun", true) -> {
                binding.apply {
                    rootLayout.setBackgroundColor(R.color.sunny)
                    currentWeatherLayout.setBackgroundResource(R.drawable.forest_sunny)
                }
                val bitMap: Bitmap = getBitmapResource(R.drawable.forest_sunny)
                updateStatusBarColor(bitMap)
            }
            currentWeather.weather[0].description.contains("cloud", true) -> {
                binding.apply {
                    rootLayout.setBackgroundColor(R.color.cloudy)
                    currentWeatherLayout.setBackgroundResource(R.drawable.forest_cloudy)
                }
                val bitMap: Bitmap = getBitmapResource(R.drawable.forest_cloudy)
                updateStatusBarColor(bitMap)
            }
        }
    }

    private fun getBitmapResource(drawable: Int): Bitmap {
        return BitmapFactory.decodeResource(
            resources,
            drawable
        )
    }

    @ExperimentalCoroutinesApi
    private fun fetchCurrentLocation() {
        lifecycleScope.launchWhenStarted {
            viewModel.getCurrentLocation().collect { lastLocation ->
                cancel("canceling location updates $lastLocation")
                viewModel.getCurrentWeather(
                    lastLocation.latitude.toString(),
                    lastLocation.longitude.toString(),
                    Constants.API_KEY
                )
                viewModel.getWeatherForecast(
                    lastLocation.latitude.toString(),
                    lastLocation.longitude.toString(),
                    Constants.API_KEY
                )
            }
        }.invokeOnCompletion {
            fetchCurrentWeather()
            fetchWeatherForecast()
        }
    }

    @ExperimentalCoroutinesApi
    private fun checkForLocationPermission() {
        if (isLocationPermissionEnabled()) {
            if (isLocationEnabled()) {
                fetchCurrentLocation()
            } else {
                Timber.e("Location not enabled")
                askForLocation()
            }
        } else {
            Timber.e("Location Permission Not enabled")
            requestLocationPermission()
        }
    }

    @ExperimentalCoroutinesApi
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            REQUEST_PERMISSION_CODE
        )
    }

    @ExperimentalCoroutinesApi
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        fetchCurrentLocation()
                    }
                }
            }
        }
    }


    private fun askForLocation() {

        MaterialAlertDialogBuilder(this).apply {
            setMessage("Enable location to continue")
            setPositiveButton("ENABLE") { dialog, _ ->
                val packageName = BuildConfig.APPLICATION_ID
                dialog.dismiss()
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:$packageName")
                    startActivity(this)
                }
            }
            setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
                exitProcess(0)
            }
            show()
        }
    }

    private fun updateStatusBarColor(bitMap: Bitmap) {

        Palette.Builder(bitMap)
            .generate { result ->

                result?.let {
                    val dominantSwatch = it.dominantSwatch

                    if (dominantSwatch != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            val window: Window = window
                            window.statusBarColor = dominantSwatch.rgb

                        } else {
                            val window: Window = window
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                            window.statusBarColor = dominantSwatch.rgb
                        }
                    }
                }
            }
    }

    companion object {
        const val REQUEST_PERMISSION_CODE = 1
    }
}