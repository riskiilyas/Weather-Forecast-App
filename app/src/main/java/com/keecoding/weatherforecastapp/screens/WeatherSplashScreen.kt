package com.keecoding.weatherforecastapp.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.keecoding.weatherforecastapp.R
import com.keecoding.weatherforecastapp.navigation.WeatherScreens
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            )
        )
        delay(1500L)
        navController.navigate(WeatherScreens.MainScreen.name)
    })
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .scale(scale.value)
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
                contentDescription = "Sunny",
                modifier = Modifier.width(150.dp))
            Text(text = "How's the weather?",
                style = MaterialTheme.typography.h5
            )

        }
    }
}