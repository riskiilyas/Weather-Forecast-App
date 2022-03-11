package com.keecoding.weatherforecastapp.screens.main

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keecoding.weatherforecastapp.data.DataOrException
import com.keecoding.weatherforecastapp.data.SharedPref
import com.keecoding.weatherforecastapp.model.Weather
import com.keecoding.weatherforecastapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val sharedPref: SharedPref,
    private val application: Application
): ViewModel() {
    private var currentCity = sharedPref.city.toString()

    suspend fun getWeather(city: String = currentCity): DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(city)
    }

    suspend fun setCity(city: String) {
        viewModelScope.launch {
            val data = getWeather(city).data
            if (data!=null) {
                sharedPref.city = city
                currentCity = city
            } else {
                Toast.makeText(application, "Can't find $city!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}