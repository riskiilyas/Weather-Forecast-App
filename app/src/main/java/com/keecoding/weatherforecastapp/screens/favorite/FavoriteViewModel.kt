package com.keecoding.weatherforecastapp.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keecoding.weatherforecastapp.data.SharedPref
import com.keecoding.weatherforecastapp.model.Favorite
import com.keecoding.weatherforecastapp.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: WeatherDbRepository,
    private val sharedPref: SharedPref
): ViewModel() {
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()
    var listCity: MutableSet<String>? = sharedPref.listFavs

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect { listOfFavs ->
                    if (listOfFavs.isNullOrEmpty()) {
                        Log.d("taglist", ": EMPTY")
                    } else {
                        _favList.value = listOfFavs
                        Log.d("taglist", favList.value.toString())
                    }
                }
        }
    }

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.insertFavorite(favorite)
        }
        val list = sharedPref.listFavs
        list?.add(favorite.city)
        list?.distinct()
        sharedPref.listFavs = list
        listCity = list
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }
        val list = sharedPref.listFavs
        list?.remove(favorite.city)
        sharedPref.listFavs = list
        listCity = list
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}