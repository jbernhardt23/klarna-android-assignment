package com.example.klarnaweather.data

import com.example.klarnaweather.data.models.Weather
import retrofit2.Retrofit

import retrofit2.http.GET

import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path


interface WeatherService {

    companion object {
        fun create(): WeatherService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.darksky.net/forecast/2bb07c3bece89caf533ac9a5d23d8417/")
                .build()

            return retrofit.create(WeatherService::class.java)

        }
    }

    @GET("{latlong}")
    fun getWeatherByLocation(@Path("latlong") latlong: String) : Call<Weather>
}