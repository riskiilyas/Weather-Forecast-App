package com.keecoding.weatherforecastapp.repository

import com.keecoding.weatherforecastapp.data.DataOrException
import com.keecoding.weatherforecastapp.model.Weather
import com.keecoding.weatherforecastapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi
){

    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(location = cityQuery)
        } catch (e: Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}