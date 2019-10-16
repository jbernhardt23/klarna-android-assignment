package com.example.klarnaweather.main

import com.example.klarnaweather.data.models.Weather

interface MainView {

    /**
     * show snackbar with custom message.
     * @param message
     */
    fun showToast(message: String)

    /**
     * show loading
     */
    fun showLoading()

    /**
     * hide loading
     */
    fun hideLoading()


    /**
     * Update view with weather data
     * @param weather
     */
    fun updateWeatherInfo(weather: Weather)


    /**
     * last known location listener
     */
    fun getLastKnownLocation()


    /**
     * Request location permissions
     */
    fun requestLocationPermission()
}