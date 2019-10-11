package com.example.klarnaweather.main

import com.example.klarnaweather.data.models.Weather

class MainPresenter(
    private var mainView: MainView?,
    private val mainInteractor: MainInteractor
) : MainInteractor.OnMainListener {

    override fun weatherLoadFailure(message: String) {
        mainView?.apply {
            hideLoading()
            showToast(message)
        }
    }

    override fun weatherLoadSuccess(weather: Weather?) {
        mainView?.apply {
            hideLoading()
            updateWeatherInfo(weather!!)
        }
    }

    fun onErrorShown(message: String) {
        mainView?.showToast(message)
    }

    fun getWeatherInfo(latitude: Double, longitude: Double) {
        mainView?.showLoading()
        mainInteractor.getWeather(this, latitude, longitude)
    }


    fun onDestroy() {
        mainView = null
    }
}