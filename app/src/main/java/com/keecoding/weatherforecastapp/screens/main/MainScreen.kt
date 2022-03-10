package com.keecoding.weatherforecastapp.screens.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.keecoding.weatherforecastapp.R
import com.keecoding.weatherforecastapp.data.DataOrException
import com.keecoding.weatherforecastapp.model.Weather
import com.keecoding.weatherforecastapp.utils.Constants
import com.keecoding.weatherforecastapp.utils.formatDate
import com.keecoding.weatherforecastapp.widgets.WeatherAppBar

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getWeather("Surabaya")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weatherData.data!!, navController)
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    
    Scaffold(
        topBar = {
            WeatherAppBar(
                navController = navController,
                title = "${weather.city.name}, ${weather.city.country}",
                elevation = 2.dp)
        }
    ) {
        MainContent(data = weather)
    }

}

@Composable
fun MainContent(data: Weather?) {
    Column(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = formatDate(data!!.list[0].dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color.Blue
        ) {
            val deg = data.list[0].temp.day

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(data.list[0].weather[0].icon)
                Text(text = "${deg.toInt()}\u2103",
                    color = Color.White,
                    style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold
                )
                Text(text = data.list[0].weather[0].main,
                    color = Color.White,
                    fontStyle = FontStyle.Italic
                )

            }

        }
        HumidityWindPressureRow(weather = data)
        Divider()
    }
}

@Composable
fun HumidityWindPressureRow(weather: Weather?) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity), contentDescription = "Humidity",
            modifier = Modifier.size(20.dp))
            Text(text = "${weather!!.list[0].humidity}%", modifier = Modifier.padding(start = 2.dp))
        }
        
        Row() {
            Icon(painter = painterResource(id = R.drawable.pressure), contentDescription = "Pressure",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather!!.list[0].pressure} psi", modifier = Modifier.padding(start = 2.dp))
        }
        
        Row() {
            Icon(painter = painterResource(id = R.drawable.wind), contentDescription = "Wind",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather!!.list[0].speed} mph", modifier = Modifier.padding(start = 2.dp))
        }
        
    }
}

@Composable
fun WeatherStateImage(code: String) {
    Image(painter = rememberImagePainter(Constants.IMAGE_URL + code + ".png"),
        contentDescription = "Weather Image",
        modifier = Modifier.size(80.dp)
    )
}
