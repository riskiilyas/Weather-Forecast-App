package com.keecoding.weatherforecastapp.data

import android.app.Application
import android.content.Context
import javax.inject.Inject

class SharedPref @Inject constructor(
    application: Application
) {
    private val pref = application.getSharedPreferences("weather_shared_pref", Context.MODE_PRIVATE)

    var city
        get() = pref.getString("city", "Surabaya")
        set(value) {
            pref.edit().putString("city", value).apply()
        }

}