package com.dvt.data.di

import androidx.room.Room
import com.dvt.data.database.WeatherDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

private val databaseModule: Module = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            "dvt-weather-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

private val daoModule: Module = module {
    single { get<WeatherDatabase>().currentWeatherDao() }
    single { get<WeatherDatabase>().weatherForecastDao() }
    single { get<WeatherDatabase>().favouritesDao() }
}

val dataModules: List<Module> = listOf(
    databaseModule,
    daoModule
)