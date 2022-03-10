package com.keecoding.weatherforecastapp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.keecoding.weatherforecastapp.R
import com.keecoding.weatherforecastapp.model.Weather
import com.keecoding.weatherforecastapp.model.WeatherObject
import com.keecoding.weatherforecastapp.utils.Constants
import com.keecoding.weatherforecastapp.utils.formatDate
import com.keecoding.weatherforecastapp.utils.formatTime

@Composable
fun WeatherDetailRow(weather: WeatherObject) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        elevation = 2.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = formatDate(weather.dt).substring(0, 3), modifier = Modifier.padding(start = 8.dp))
            WeatherStateImage(code = weather.weather[0].icon)
            Column(
                modifier = Modifier.padding(end = 8.dp),
                horizontalAlignment = Alignment.End
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.Blue
                ) {
                    Text(text = weather.weather[0].description,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(start = 6.dp, end = 6.dp)
                    )
                }
                Text(text = "${weather.temp.min.toInt()}°C - ${weather.temp.max.toInt()}°C",
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp, end = 4.dp),
                    textAlign = TextAlign.End
                )
            }


        }
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

        Row {
            Icon(painter = painterResource(id = R.drawable.pressure), contentDescription = "Pressure",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather!!.list[0].pressure} psi", modifier = Modifier.padding(start = 2.dp))
        }

        Row {
            Icon(painter = painterResource(id = R.drawable.wind), contentDescription = "Wind",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather!!.list[0].speed} mph", modifier = Modifier.padding(start = 2.dp))
        }

    }
}

@Composable
fun SunsetAndSunrise(weather: Weather) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 32.dp, bottom = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(painter = painterResource(id = R.drawable.sunrise), contentDescription = "Sunrise",
                modifier = Modifier.size(20.dp)
            )
            Text(text = formatTime(weather.list[0].sunrise), modifier = Modifier.padding(start = 2.dp))
        }

        Row {
            Text(text = formatTime(weather.list[0].sunset))
            Icon(painter = painterResource(id = R.drawable.sunset), contentDescription = "Sunset",
                modifier = Modifier
                    .size(20.dp)
                    .padding(start = 4.dp)
            )
        }

    }
}

@Composable
fun WeatherStateImage(code: String, size: Dp = 80.dp) {
    Image(painter = rememberImagePainter(Constants.IMAGE_URL + code + ".png"),
        contentDescription = "Weather Image",
        modifier = Modifier.size(size)
    )
}
