package com.keecoding.weatherforecastapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.keecoding.weatherforecastapp.data.SharedPref
import com.keecoding.weatherforecastapp.navigation.WeatherNavigation
import com.keecoding.weatherforecastapp.ui.theme.WeatherForecastAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        var currentCity: String = ""
        var mainActivity: MainActivity? = null
    }

    var loc = mutableListOf<Double>()
    @Inject lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivity = this
//        Dexter.withContext(this)
//            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//            .withListener(object : PermissionListener {
//                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
//                    val locationManager: LocationManager =
//                        getSystemService(LOCATION_SERVICE) as LocationManager
//                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
//                    } else{
//                        getCurrentLocation(locationManager)
//                    }
//                    if (loc.isNotEmpty()) {
//                        val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
//                        val adderesses = geocoder.getFromLocation(loc[0], loc[1], 1)
//                        currentCity = adderesses[0].getAddressLine(0)
//                    } else {
//                        currentCity = sharedPref.city.toString()
//                    }
//            }
//
//            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
//                Toast.makeText(this@MainActivity, "Enable GPS on Setting to update automatically!", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onPermissionRationaleShouldBeShown(
//                p0: PermissionRequest?,
//                p1: PermissionToken?
//            ) {
//                p1?.continuePermissionRequest();
//            }
//        })
//        .check();
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp()
        }
    }

    private fun getCurrentLocation(locationManager: LocationManager) {
        if (ActivityCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val locationGPS: Location? =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (locationGPS != null) {
                loc.add(locationGPS.latitude)
                loc.add(locationGPS.longitude)
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun WeatherApp() {
    WeatherForecastAppTheme(
        darkTheme = false
    ) {
        Surface(color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherNavigation()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherForecastAppTheme {
    }
}