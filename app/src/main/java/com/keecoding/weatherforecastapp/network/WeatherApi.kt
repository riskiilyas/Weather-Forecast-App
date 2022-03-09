package com.keecoding.weatherforecastapp.network

import com.keecoding.weatherforecastapp.model.Weather
import com.keecoding.weatherforecastapp.utils.Constants
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET("/data/2.5/forecast/daily?q=lisbon&appid=${Constants.API_KEY}&units=imperial")
    suspend fun getWeather(): Weather

}