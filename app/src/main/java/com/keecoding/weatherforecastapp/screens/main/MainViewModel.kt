package com.keecoding.weatherforecastapp.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keecoding.weatherforecastapp.data.DataOrException
import com.keecoding.weatherforecastapp.model.Weather
import com.keecoding.weatherforecastapp.model.WeatherX
import com.keecoding.weatherforecastapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository

): ViewModel() {

    suspend fun getWeather(city: String): DataOrException<Weather, Boolean, Exception> {
        var rslt =  repository.getWeather(city)
        if (rslt.data==null) {
            rslt = repository.getWeather("Surabaya")
        }
        return rslt
    }
}