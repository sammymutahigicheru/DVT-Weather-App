package com.dvt.dvtweatherapp.di

import com.dvt.dvtweatherapp.viewmodel.WeatherViewModel
import com.dvt.dvtweatherapp.repository.WeatherRepository
import com.dvt.dvtweatherapp.repository.WeatherRepositoryImplementation
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<WeatherRepository> { WeatherRepositoryImplementation(get(),get(),get()) }
}

val viewModelModule: Module = module {
    viewModel { WeatherViewModel(get(), get()) }
}

val locationModule: Module = module {
    single {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }
}


val appModules: List<Module> = listOf(
    repositoryModule,
    viewModelModule,
    locationModule
)