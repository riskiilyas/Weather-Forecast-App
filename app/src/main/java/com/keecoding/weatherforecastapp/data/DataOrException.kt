package com.keecoding.weatherforecastapp.data

class DataOrException<T, Boolean, E: Exception> (
    var data: T? = null,
    var loading: kotlin.Boolean? = null,
    e: E? = null
)