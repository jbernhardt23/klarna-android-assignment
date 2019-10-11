package com.example.klarnaweather.main

import android.util.Log
import com.example.klarnaweather.data.WeatherService
import com.example.klarnaweather.data.models.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainInteractor {

    interface OnMainListener {
        fun weatherLoadSuccess(weather: Weather?)
        fun weatherLoadFailure(message: String)
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
        weatherService.getWeatherByLocation("$latitude,$longitude")
            .enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    val weather = response.body()
                    val statusCode = response.code()
                    if(statusCode == 200){
                      listener.weatherLoadSuccess(weather)
                    }else{
                        listener.weatherLoadFailure(response.message())
                    }
                }
                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    listener.weatherLoadFailure(t.message!!)
                }
            })
    }


}