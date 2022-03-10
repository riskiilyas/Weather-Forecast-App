package com.keecoding.weatherforecastapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun formatDate(timestamp: Int): String{
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}

@SuppressLint("SimpleDateFormat")
fun formatDateTime(timestamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}

@SuppressLint("SimpleDateFormat")
fun formatTime(stamp: Int): String {
    val timestamp: Long = stamp.toLong()
    val timeD = Date(timestamp * 1000)
    val sdf = SimpleDateFormat("HH:mm")

    return sdf.format(timeD)
}