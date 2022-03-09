package com.keecoding.weatherforecastapp.model


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<WeatherObject>,
    @SerializedName("message")
    val message: Double
)