package com.example.klarnaweather.data.models

data class CurrentWeather(
    val time: Double, val summary: String, val icon: String,
    val precipIntensity: Double,
    val precipProbability: Double,
    val precipType: String,
    val temperature: Double,
    val apparentTemperature: Double,
    val dewPoint: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windGust: Double,
    val windBearing: Double,
    val cloudCover: Double,
    val uvIndex: Double,
    val visibility: Double,
    val ozone: Double
)