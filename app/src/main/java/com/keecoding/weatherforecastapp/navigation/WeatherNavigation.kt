package com.keecoding.weatherforecastapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.keecoding.weatherforecastapp.screens.WeatherSplashScreen
import com.keecoding.weatherforecastapp.screens.main.MainScreen
import com.keecoding.weatherforecastapp.screens.main.MainViewModel
import com.keecoding.weatherforecastapp.screens.search.SearchScreen

@Composable
fun WeatherNavigation() {
    val navController =  rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        composable(WeatherScreens.MainScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, mainViewModel = mainViewModel)
        }
        
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
    }
}