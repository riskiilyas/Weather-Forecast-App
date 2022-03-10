package com.keecoding.weatherforecastapp.network

import com.keecoding.weatherforecastapp.model.Weather
import com.keecoding.weatherforecastapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET("/data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") location: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY
    ): Weather

}