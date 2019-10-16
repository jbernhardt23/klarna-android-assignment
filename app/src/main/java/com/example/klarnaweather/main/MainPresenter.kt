package com.example.klarnaweather.main

import com.example.klarnaweather.R
import com.example.klarnaweather.data.WeatherService
import com.example.klarnaweather.data.models.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val REQUEST_CODE = 1
const val PERMISSION_DENIED_MESSAGE = "Location permission denied"

class MainPresenter(private var mainView: MainView?) {


    private val weatherService by lazy {
        WeatherService.create()
    }


    /**
     * Call Weather service to get current weather based on users location
     * @param latitude
     * @param longitude
     */
    fun getWeatherInfo(latitude: Double, longitude: Double) {
        mainView?.showLoading()
        weatherService.getWeatherByLocation("$latitude,$longitude")
            .enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    val weather = response.body()
                    val statusCode = response.code()
                    if (statusCode == 200) {
                        mainView?.apply {
                            hideLoading()
                            updateWeatherInfo(weather!!)
                        }
                    } else {
                        mainView?.showToast(response.message())
                    }
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    mainView?.showToast(t.message!!)
                }
            })

    }

    /**
     * Request last known location if permissions is already granted.
     * @param isGranted
     */
    fun checkPermissionIsGranted(isGranted: Boolean) {
        if (isGranted)
            mainView?.getLastKnownLocation()
        else
            mainView?.requestLocationPermission()
    }


    /**
     * Upon permission dialog result, request last known location
     * if permissions is granted.
     * @param requestCode
     * @param grantResults
     * @param permissionGranted
     */
    fun permissionResult(requestCode: Int, grantResults: IntArray, permissionGranted: Int) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isEmpty()) {
                mainView?.hideLoading()
            } else if (grantResults[0] == permissionGranted) {
                mainView?.getLastKnownLocation()
            } else {
                mainView?.apply {
                    hideLoading()
                    showToast(PERMISSION_DENIED_MESSAGE)
                }
            }
        }
    }


    fun onDestroy() {
        mainView = null
    }
}