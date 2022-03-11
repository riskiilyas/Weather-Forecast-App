package com.keecoding.weatherforecastapp.repository

import com.keecoding.weatherforecastapp.data.WeatherDao
import com.keecoding.weatherforecastapp.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(
    private val weatherDao: WeatherDao
) {
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getAll()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun deleteAll() = weatherDao.deleteAll()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFav(favorite)
    fun getFavById(city: String): Flow<Favorite> = weatherDao.getByCity(city)
}