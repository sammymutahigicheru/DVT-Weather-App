package com.dvt.dvtweatherapp.ui.weather.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
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
import com.dvt.core.extensions.navigate
import com.dvt.core.extensions.setImageTint
import com.dvt.core.extensions.showErrorDialog
import com.dvt.core.extensions.showSnackbar
import com.dvt.core.helpers.isOnline
import com.dvt.dvtweatherapp.BuildConfig
import com.dvt.dvtweatherapp.R
import com.dvt.dvtweatherapp.data.Forecast
import com.dvt.dvtweatherapp.data.Weather
import com.dvt.dvtweatherapp.databinding.ActivityMainBinding
import com.dvt.dvtweatherapp.ui.favourites.main.FavouriteActivity
import com.dvt.dvtweatherapp.ui.weather.adapter.WeeklyForecastAdapter
import com.dvt.dvtweatherapp.ui.weather.viewmodel.WeatherViewModel
import com.dvt.dvtweatherapp.utils.BackgroundControllers
import com.dvt.dvtweatherapp.utils.Constants
import com.dvt.dvtweatherapp.utils.ResponseState
import com.dvt.dvtweatherapp.utils.extensions.isLocationEnabled
import com.dvt.dvtweatherapp.utils.extensions.isLocationPermissionEnabled
import com.dvt.dvtweatherapp.utils.mappers.toFavourites
import com.dvt.dvtweatherapp.utils.mappers.toForecast
import com.dvt.dvtweatherapp.utils.mappers.toWeather
import com.dvt.network.models.CurrentWeatherResponse
import com.dvt.network.models.OneShotForeCastResponse
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.IOException
import java.util.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: WeatherViewModel by viewModel()

    private lateinit var progressDialog: MaterialAlertDialogBuilder

    private val isOnline by lazy {
        isOnline(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = MaterialAlertDialogBuilder(this).apply {
            setCancelable(false)
        }
    }

    @ExperimentalCoroutinesApi
    override fun onStart() {
        super.onStart()
        checkForLocationPermission()
    }

    private fun initListeners(currentWeather: Weather) {
        val description = currentWeather.description
        when {
            description.contains(BackgroundControllers.RAINY, true) -> {
                binding.favoritesFloatingActionButton.setImageTint(R.color.rainy)
            }
            description.contains(BackgroundControllers.SUNNY, true) -> {
                binding.favoritesFloatingActionButton.setImageTint(R.color.sunny)
            }
            description.contains(BackgroundControllers.CLOUDY, true) -> {
                binding.favoritesFloatingActionButton.setImageTint(R.color.cloudy)
            }
        }
        binding.favoritesFloatingActionButton.setOnClickListener {
            navigate<FavouriteActivity>()
        }
    }

    private fun fetchWeatherForecast() {
        lifecycleScope.launchWhenStarted {
            val progress = progressDialog.create()
            viewModel.weatherForecastState.collect { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        progress.setMessage(getString(R.string.loading_weather_forecast))
                        progress.show()
                    }
                    is ResponseState.Result<*> -> {
                        progress.dismiss()
                        val result: OneShotForeCastResponse = state.data as OneShotForeCastResponse
                        setUpRecyclerView(result.toForecast())
                    }
                    is ResponseState.Error -> {
                        Timber.e("Error: ${state.message}")
                        progress.dismiss()
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView(result: List<Forecast>) {
        val weeklyForecastAdapter = WeeklyForecastAdapter()
        binding.weeklyForecastRecyclerview.apply {
            adapter = weeklyForecastAdapter
            hasFixedSize()
        }
        weeklyForecastAdapter.submitList(result)
    }

    private fun fetchCurrentWeather() {
        lifecycleScope.launchWhenStarted {
            val progress = progressDialog.create()
            viewModel.currentWeatherState.collect { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        progress.setMessage(getString(R.string.loading_current_weather))
                        progress.show()
                    }
                    is ResponseState.Result<*> -> {
                        progress.dismiss()
                        val currentWeather: CurrentWeatherResponse =
                            state.data as CurrentWeatherResponse
                        val locationName =
                            getLocationName(currentWeather.coord.lat, currentWeather.coord.lon)
                        currentWeather.locationName = locationName
                        setUpView(currentWeather.toWeather())
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
    private fun setUpView(currentWeather: Weather) {

        Timber.e("Current Weather $currentWeather")
        binding.apply {
            temperatureTextview.text = "${currentWeather.currentTemperature} ℃"
            weatherDescriptionTextview.text = currentWeather.description
            minimumTemperatureTexview.text = "${currentWeather.minimumTemperature} ℃"
            currentTemperatureTextview.text = "${currentWeather.currentTemperature} ℃"
            maximumTemperatureTitle.text = "${currentWeather.maximumTemperature} ℃"
        }
        updateBackground(currentWeather)
        saveCurrentWeatherAsFavourite(currentWeather)
        initListeners(currentWeather)
    }

    private fun saveCurrentWeatherAsFavourite(currentWeather: Weather) {
        binding.apply {
            favouriteIcon.setOnClickListener {
                favouriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
                //update favourites column
                currentWeather.isFavourite = 1
                viewModel.saveCurrentWeatherAsFavorite(currentWeather.toFavourites())
                    .invokeOnCompletion {
                        this@MainActivity.runOnUiThread {
                            val message = getString(R.string.save_success_message)
                            binding.root.showSnackbar(message)
                        }
                    }
            }
        }
    }

    private fun getLocationName(lat: Double, lon: Double): String {
        val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lon, 1)
            val address: Address = addresses[0]
            Timber.e("City Name: ${address.adminArea}")
            return address.adminArea
        } catch (e: Throwable) {
            throw IOException(e)
        }
    }

    private fun updateBackground(currentWeather: Weather) {
        when {
            currentWeather.description.contains(BackgroundControllers.RAINY, true) -> {
                binding.apply {
                    rootLayout.setBackgroundColor(resources.getColor(R.color.rainy))
                    currentWeatherLayout.setBackgroundResource(R.drawable.forest_rainy)
                }
                val bitMap: Bitmap = getBitmapResource(R.drawable.forest_rainy)
                updateStatusBarColor(bitMap)
            }
            currentWeather.description.contains(BackgroundControllers.SUNNY, true) -> {
                binding.apply {
                    rootLayout.setBackgroundColor(resources.getColor(R.color.sunny))
                    currentWeatherLayout.setBackgroundResource(R.drawable.forest_sunny)
                }
                val bitMap: Bitmap = getBitmapResource(R.drawable.forest_sunny)
                updateStatusBarColor(bitMap)
            }
            currentWeather.description.contains(BackgroundControllers.CLOUDY, true) -> {
                binding.apply {
                    rootLayout.setBackgroundColor(resources.getColor(R.color.cloudy))
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
                if (isOnline) {
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

                    //collect current weather and forecast
                    fetchCurrentWeather()
                    fetchWeatherForecast()

                } else {
                    //let the user know the app is offline
                    binding.root.showSnackbar("Offline Mode")

                    fetchOfflineCurrentWeather()
                    fetchOfflineWeatherForecast()
                }
            }
        }
    }

    private fun fetchOfflineWeatherForecast() {
        lifecycleScope.launchWhenStarted {
            viewModel.fetchOfflineWeatherForecast().collect { forecast ->
                setUpRecyclerView(forecast.toForecast())
            }
        }
    }

    private fun fetchOfflineCurrentWeather() {
        lifecycleScope.launchWhenStarted {
            viewModel.fetchOfflineCurrentWeather().collect { currentWeather ->
                setUpView(currentWeather.toWeather())
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun checkForLocationPermission() {
        if (isLocationPermissionEnabled()) {
            if (isLocationEnabled()) {
                fetchCurrentLocation()
            } else {
                Timber.e(getString(R.string.location_not_enabled))
                showErrorDialog("GPS Location Not Enabled","Enable GPS Location To Continue",shouldExit = true ){
                    checkForLocationPermission()
                }
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

    @SuppressLint("MissingSuperCall")
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
                        //check permissions again incase the user has not enabled gps it will prompt the user to turn on gps
                        checkForLocationPermission()
                    }
                }
            }
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
