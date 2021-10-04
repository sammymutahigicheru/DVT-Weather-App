package com.dvt.dvtweatherapp.favourites

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dvt.data.database.WeatherDatabase
import com.dvt.data.database.dao.FavouritesDao
import com.dvt.dvtweatherapp.ui.favourites.repository.FavouritesRepository
import com.dvt.dvtweatherapp.ui.favourites.repository.FavouritesRepositoryImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Config.OLDEST_SDK], manifest = Config.NONE)
class FavoritesRepositoryTest {
    private lateinit var favoriteRepository: FavouritesRepository
    private lateinit var database: WeatherDatabase
    private lateinit var favouritesDao: FavouritesDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, WeatherDatabase::class.java).build()
        favouritesDao = database.favouritesDao()
        favoriteRepository = FavouritesRepositoryImpl(favouritesDao = favouritesDao)
    }

    @Test
    fun `when fetching favorites request is made, then return all saved favorites`() {
        runBlocking(Dispatchers.IO) {
            //save favorite item to db
            favouritesDao.insert(SampleFavoriteData.favourite)
            //get all favorite items
            val favorites = favoriteRepository.fetchFavourites()

            //collect favorites
            favorites.collect { favoritesList ->
                Truth.assertThat(favoritesList).isEqualTo(listOf(SampleFavoriteData.favourite))
            }
        }
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