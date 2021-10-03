package com.dvt.dvtweatherapp.di

import com.dvt.dvtweatherapp.ui.weather.repository.WeatherRepository
import com.dvt.dvtweatherapp.ui.weather.repository.WeatherRepositoryImplementation
import com.dvt.dvtweatherapp.ui.favourites.repository.FavouritesRepository
import com.dvt.dvtweatherapp.ui.favourites.repository.FavouritesRepositoryImpl
import com.dvt.dvtweatherapp.ui.favourites.viewmodel.FavouritesViewModel
import com.dvt.dvtweatherapp.ui.weather.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val repositoryModule: Module = module {
    single<WeatherRepository> { WeatherRepositoryImplementation(get(), get(), get(), get()) }
    single<FavouritesRepository> { FavouritesRepositoryImpl(get()) }
}

private val viewModelModule: Module = module {
    viewModel { WeatherViewModel(get(), get()) }
    viewModel { FavouritesViewModel(get()) }
}

private val locationModule: Module = module {
    single {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }
}


val appModules: List<Module> = listOf(
    repositoryModule,
    viewModelModule,
    locationModule
)