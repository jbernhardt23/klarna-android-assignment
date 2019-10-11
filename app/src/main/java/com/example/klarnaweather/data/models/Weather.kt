package com.example.klarnaweather.data.models

import com.google.gson.annotations.SerializedName

data class Weather(
    val latitude: Double, val longitude: Double,
    val timezone: String,
    @SerializedName("currently") val currentWeather: CurrentWeather
)