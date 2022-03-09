package com.keecoding.weatherforecastapp.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keecoding.weatherforecastapp.data.DataOrException
import com.keecoding.weatherforecastapp.model.Weather
import com.keecoding.weatherforecastapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository

): ViewModel() {

    val data: MutableState<DataOrException<Weather, Boolean, Exception>>
        = mutableStateOf(DataOrException(null, true, Exception("")))


    init {
       loadWeather("Seattle")
    }

    private fun loadWeather(city: String) {
        viewModelScope.launch {
            if (city.isBlank()) return@launch
            data.value.loading = true
            data.value = repository.getWeather(cityQuery = city)
            if (data.value.data.toString().isNotBlank()) data.value.loading = false
        }
        Log.d("tagg", "loadWeather: ${data.value.data.toString()} ")
    }
}