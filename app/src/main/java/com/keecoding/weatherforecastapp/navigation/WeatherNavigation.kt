package com.keecoding.weatherforecastapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.keecoding.weatherforecastapp.screens.about.AboutScreen
import com.keecoding.weatherforecastapp.screens.favorite.FavoriteScreen
import com.keecoding.weatherforecastapp.screens.splashscreen.WeatherSplashScreen
import com.keecoding.weatherforecastapp.screens.main.MainScreen
import com.keecoding.weatherforecastapp.screens.main.MainViewModel
import com.keecoding.weatherforecastapp.screens.search.SearchScreen
import com.keecoding.weatherforecastapp.screens.setting.SettingScreen

@Composable
fun WeatherNavigation() {
    val navController =  rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable(route="$route/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.getString("city")?.let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController,
                    mainViewModel = mainViewModel,
                    city = city
                )
            }
        }
        
        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.SettingScreen.name) {
            SettingScreen(navController = navController)
        }

        composable(WeatherScreens.FavoriteScreen.name) {
            FavoriteScreen(navController = navController)
        }
    }
}