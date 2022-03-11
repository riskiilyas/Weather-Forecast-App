package com.keecoding.weatherforecastapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.keecoding.weatherforecastapp.data.WeatherDb
import com.keecoding.weatherforecastapp.network.WeatherApi
import com.keecoding.weatherforecastapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDB(@ApplicationContext application: Context): WeatherDb{
        return Room.databaseBuilder(
            application,
            WeatherDb::class.java,
            "Weather_DB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}