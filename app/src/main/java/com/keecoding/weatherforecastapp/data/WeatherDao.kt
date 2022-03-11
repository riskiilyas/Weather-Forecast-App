package com.keecoding.weatherforecastapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.Query
import com.keecoding.weatherforecastapp.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = ABORT)
    suspend fun insertFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite where city = :city")
    fun getByCity(city: String): Flow<Favorite>

    @Query("SELECT * FROM favorite")
    fun getAll(): Flow<List<Favorite>>

    @Delete
    suspend fun deleteFav(favorite: Favorite)

    @Query("DELETE from favorite")
    suspend fun deleteAll()
}