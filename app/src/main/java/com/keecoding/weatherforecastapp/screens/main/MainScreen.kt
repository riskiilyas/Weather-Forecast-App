package com.keecoding.weatherforecastapp.screens.main

import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.keecoding.weatherforecastapp.data.DataOrException
import com.keecoding.weatherforecastapp.model.Weather
import com.keecoding.weatherforecastapp.navigation.WeatherScreens
import com.keecoding.weatherforecastapp.screens.favorite.FavoriteViewModel
import com.keecoding.weatherforecastapp.utils.formatDate
import com.keecoding.weatherforecastapp.widgets.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    city: String?,
) {
    runBlocking {
        city?.let { if (it!="null")  mainViewModel.setCity(it) }
    }

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {
        delay(100)
        value = mainViewModel.getWeather()
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weatherData.data!!, navController)
    }

    val context = LocalContext.current
    BackHandler(true) {
        var currentContext = context
        while (currentContext is ContextWrapper) {
            if (currentContext is ComponentActivity) {
                currentContext.finish()
            }
            currentContext = currentContext.baseContext
        }
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, vm: FavoriteViewModel = hiltViewModel()) {
    var isFavorite = false
    runBlocking {
        vm.favList.value.forEach {
            if (weather.city.name == it.city) isFavorite = true
        }
    }

    Scaffold(
        topBar = {
            WeatherAppBar(
                navController = navController,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name,
                        navOptions = NavOptions.Builder().setPopUpTo(WeatherScreens.MainScreen.name, true).build()
                    )
                },
                title = "${weather.city.name}, ${weather.city.country}",
                elevation = 2.dp,
                weather = weather,
                favorite = isFavorite
            )
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
        horizontalAlignment = CenterHorizontally
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
                horizontalAlignment = CenterHorizontally
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
        SunsetAndSunrise(weather = data)
        Text(text = "This Week", modifier = Modifier.align(CenterHorizontally),
            style = MaterialTheme.typography.h6
        )
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                itemsIndexed(items = data.list) { idx, item ->
                    WeatherDetailRow(weather = item)
                }
            }
            
        }
    }
}

