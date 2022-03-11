package com.keecoding.weatherforecastapp.screens.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceAround
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceEvenly
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import coil.compose.rememberImagePainter
import com.keecoding.weatherforecastapp.model.Favorite
import com.keecoding.weatherforecastapp.navigation.WeatherScreens
import com.keecoding.weatherforecastapp.utils.Constants
import com.keecoding.weatherforecastapp.widgets.WeatherAppBar

@Composable
fun FavoriteScreen(navController: NavController,
                   favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        WeatherAppBar(navController = navController,
        title = "Favorites",
        icon = Icons.Default.ArrowBack,
        isMainScreen = false
    ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                val list = favoriteViewModel.favList.collectAsState().value
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = list) {
                        CityRow(it, navController, favoriteViewModel)
                    }
                }
            }
        }

    }

}

@Composable
fun CityRow(fav: Favorite, navController: NavController, favoriteViewModel: FavoriteViewModel) {
    Surface(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(50.dp)
        .clickable {
            navController.navigate(
                WeatherScreens.MainScreen.name + "/${fav.city}",
                navOptions = NavOptions.Builder().setPopUpTo(WeatherScreens.FavoriteScreen.name, true).build()
            )
        },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = CenterVertically,
            horizontalArrangement = SpaceBetween
        ) {
            Row(
                verticalAlignment = CenterVertically
            ) {
                Image(painter = rememberImagePainter(Constants.FLAG_URL + fav.country.lowercase()),
                    contentDescription = "Country Flag",
                    modifier = Modifier
                        .size(36.dp)
                        .padding(start = 12.dp),
                )
                Text(text = "${fav.city}, ${fav.country}",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Icon(imageVector = Icons.Rounded.Close, contentDescription = "",
                modifier = Modifier.padding(end = 12.dp)
                    .clickable {
                        favoriteViewModel.deleteFavorite(fav)
                    }
            )
        }
    }
}
