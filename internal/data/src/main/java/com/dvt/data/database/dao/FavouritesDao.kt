package com.dvt.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dvt.data.database.entities.Favourites
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao:BaseDao<Favourites> {
    @Query("SELECT * FROM favourites where isFavourite = 1")
    fun fetchFavourites(): Flow<List<Favourites>>
}