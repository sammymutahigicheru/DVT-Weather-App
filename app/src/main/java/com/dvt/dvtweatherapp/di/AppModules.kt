package com.dvt.dvtweatherapp.di

import com.dvt.dvtweatherapp.viewmodel.WeatherViewModel
import com.dvt.network.repository.WeatherRepository
import com.dvt.network.repository.WeatherRepositoryImplementation
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule:Module = module {
    single<WeatherRepository>{WeatherRepositoryImplementation(get())}
}

val viewModelModule: Module = module {
    viewModel { WeatherViewModel(get()) }
}

val appModules:List<Module> = listOf(
    repositoryModule,
    viewModelModule
)