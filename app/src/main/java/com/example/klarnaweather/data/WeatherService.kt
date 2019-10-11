package com.example.klarnaweather.data

import com.example.klarnaweather.data.models.Weather
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

interface WeatherService {

    companion object {
        fun create(): WeatherService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl("https://api.darksky.net/forecast/2bb07c3bece89caf533ac9a5d23d8417/")
                .build()

            return retrofit.create(WeatherService::class.java)

        }
    }

    @GET("./")
    fun getWeatherByLocation(@Query("latitude") latitude: Double,
                             @Query("longitude") longitude: Double) : Observable<Weather>
}