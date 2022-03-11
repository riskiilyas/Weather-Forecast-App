package com.keecoding.weatherforecastapp.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import com.keecoding.weatherforecastapp.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.keecoding.weatherforecastapp.widgets.WeatherAppBar

@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        topBar = {
            WeatherAppBar(navController = navController,
                title = "About",
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                onButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = CircleShape
                ) {
                    Image(painter = painterResource(id = R.drawable.keecodingv2),
                        contentDescription = "Developer Profile",
                        Modifier.size(100.dp)
                    )
                }
                Text(text = "Developed by Riski (KeeCoding)",
                    modifier = Modifier.padding(top = 12.dp)
                )
                Text(text = "Weather Forecast App V.1.0",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(text = "Powered By:",
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(text = "openweathermap.org",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            
        }
    }
}