package com.keecoding.weatherforecastapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.keecoding.weatherforecastapp.R
import com.keecoding.weatherforecastapp.navigation.WeatherScreens

@Composable
fun WeatherSplashScreen(navController: NavController) {
    androidx.compose.material.Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(
            2.dp, Color.Blue
        )
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.splashscreen),
                contentDescription = "",
                modifier = Modifier.width(100.dp))
            Text(text = "How's the weather!",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )

        }
    }
}