package com.keecoding.weatherforecastapp.widgets

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.keecoding.weatherforecastapp.model.Favorite
import com.keecoding.weatherforecastapp.model.Weather
import com.keecoding.weatherforecastapp.navigation.WeatherScreens
import com.keecoding.weatherforecastapp.screens.favorite.FavoriteViewModel
import com.keecoding.weatherforecastapp.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    weather: Weather? = null,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    favorite: Boolean = false,
    onButtonClicked: () -> Unit = {}
) {
    val context = LocalContext.current

    var isFavorite by remember {
        mutableStateOf(favorite)
    }

    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog,
            navController = navController
        )
    }
    TopAppBar(
        title = {
            Text(text = title,
                color = Color.DarkGray,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
            )

            weather?.let {
                Image(painter = rememberImagePainter(Constants.FLAG_URL + weather.city.country.lowercase()),
                    contentDescription = "Country Flag",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 4.dp),
                )
            }
        },
        actions = {
                  if (isMainScreen) {
                      IconButton(onClick = { onAddActionClicked.invoke() }) {
                          Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                      }

                      IconButton(onClick = {
                          showDialog.value = true
                      }) {
                          Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "More Icon")
                      }
                  } else Box {}
        },
        navigationIcon = {
             if (icon!=null) {
                 Icon(imageVector = icon, contentDescription = "",
                 tint = Color.Gray,
                 modifier = Modifier
                     .padding(start = 8.dp)
                     .clickable { onButtonClicked.invoke() }
                 )
             }

            if (isMainScreen) {
                val vector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite
                val tint = if (!isFavorite) Color.Gray else Color.Red
                Icon(imageVector = vector,
                    tint = tint,
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .scale(0.9f)
                        .clickable {
                            isFavorite = !isFavorite
                            if (isFavorite) {
                                weather?.let {
                                    favoriteViewModel.insertFavorite(
                                        Favorite(
                                            it.city.name,
                                            it.city.country
                                        )
                                    )
                                    Toast
                                        .makeText(context, "Added To Favorite!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } else {
                                weather?.let {
                                    favoriteViewModel.deleteFavorite(
                                        Favorite(
                                            it.city.name,
                                            it.city.country
                                        )
                                    )
                                    Toast
                                        .makeText(
                                            context,
                                            "Removed From Favorite!",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                        }
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )

}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {

    val items = listOf("About", "Favorites", "Settings")
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
        .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(expanded = showDialog.value,
            onDismissRequest = { showDialog.value = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    when(index) {
                        0 -> navController.navigate(WeatherScreens.AboutScreen.name)
                        1 -> navController.navigate(WeatherScreens.FavoriteScreen.name)
                        2 -> navController.navigate(WeatherScreens.SettingScreen.name)
                    }
                }) {
                    val vector = when(index) {
                        0 -> Icons.Default.Info;
                        1 -> Icons.Default.Favorite;
                        else -> Icons.Default.Settings;
                    }
                    Icon(imageVector = vector, contentDescription = item, tint = Color.Blue)
                    Text(text = item, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }

    }
}
