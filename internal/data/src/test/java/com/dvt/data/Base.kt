package com.dvt.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dvt.data.database.WeatherDatabase
import com.dvt.data.database.dao.FavouritesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import java.io.IOException

open class Base {
    private lateinit var database: WeatherDatabase
    protected lateinit var favouritesDao: FavouritesDao

    @Before
    open fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java).build()
        favouritesDao = database.favouritesDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        runBlocking(Dispatchers.IO) {
            database.clearAllTables()
        }
        database.close()
    }
}