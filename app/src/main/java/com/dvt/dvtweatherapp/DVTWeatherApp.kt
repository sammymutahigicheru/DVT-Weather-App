package com.dvt.dvtweatherapp

import android.app.Application
import com.dvt.data.di.dataModules
import com.dvt.dvtweatherapp.di.appModules
import com.dvt.network.di.networkModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.core.logger.Level
import org.koin.core.module.Module
import timber.log.Timber

class DVTWeatherApp:Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initilizeKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initilizeKoin() {
        try {
            startKoin {
                androidLogger(Level.ERROR)
                androidContext(applicationContext)
                val modules = mutableListOf<Module>().apply {
                    addAll(appModules)
                    addAll(networkModules)
                    addAll(dataModules)
                }
                modules(modules)
            }
        } catch (error: KoinAppAlreadyStartedException) {
            //log the error
        }
    }
}