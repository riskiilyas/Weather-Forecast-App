package com.keecoding.weatherforecastapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keecoding.weatherforecastapp.model.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class WeatherDb: RoomDatabase() {
    abstract val weatherDao: WeatherDao
}