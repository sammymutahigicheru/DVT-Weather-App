package com.dvt.dvtweatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dvt.dvtweatherapp.databinding.ActivityMainBinding
import com.dvt.dvtweatherapp.utils.ResponseState
import com.dvt.dvtweatherapp.viewmodel.WeatherViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: WeatherViewModel by viewModel()

    private lateinit var progressDialog: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = MaterialAlertDialogBuilder(this).apply {
            setCancelable(false)
        }

        viewModel.getCurrentWeather("-1.208571", "36.900341", "c1373764270eceaee171213e6b2560c7")
        viewModel.getWeatherForecast("-1.208571", "36.900341", "c1373764270eceaee171213e6b2560c7")
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            val progress = progressDialog.create()
            viewModel.currentWeatherState.collect { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        progress.setMessage("fetching current weather info...")
                        progress.show()
                    }
                    is ResponseState.Result<*> -> {
                        val result = state.data
                        Timber.e("Current Weather: $result")
                        progress.dismiss()

                    }
                    is ResponseState.Error -> {
                        Timber.e("Error: ${state.message}")
                        progress.dismiss()
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            val progress = progressDialog.create()
            viewModel.weatherForecastState.collect { state ->
                when (state) {
                    is ResponseState.Loading -> {
                        progress.setMessage("fetching weather forecast info...")
                        progress.show()
                    }
                    is ResponseState.Result<*> -> {
                        val result = state.data
                        Timber.e("Weather forecast: $result")
                        progress.dismiss()
                    }
                    is ResponseState.Error -> {
                        Timber.e("Error: ${state.message}")
                        progress.dismiss()
                    }
                }
            }
        }
    }
}