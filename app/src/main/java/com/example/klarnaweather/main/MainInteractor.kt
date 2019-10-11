package com.example.klarnaweather.main

import android.util.Log
import com.example.klarnaweather.data.WeatherService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainInteractor {

    interface OnMainListener {

    }

    private val weatherService by lazy {
        WeatherService.create()
    }

    /**
     * Call Weather service to get current weather based on users location
     * @param listener
     * @param latitude
     * @param longitude
     */
    fun getWeather(listener: OnMainListener, latitude: Double, longitude: Double) {
        weatherService.getWeatherByLocation(latitude, longitude).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { weather ->
                    Log.d("Weather", weather.latitude.toString())
                }, { error ->
                    Log.e("Weather", error.message)
                })
    }


}