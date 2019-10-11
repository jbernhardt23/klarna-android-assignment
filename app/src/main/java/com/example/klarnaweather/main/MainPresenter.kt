package com.example.klarnaweather.main

class MainPresenter(
    private var mainView: MainView?,
    private val mainInteractor: MainInteractor
) : MainInteractor.OnMainListener {

    fun onErrorShown(message: String) {
        mainView?.showToast(message)
    }

    fun getWeatherInfo(latitude: Double, longitude: Double) {
        mainInteractor.getWeather(this, latitude, longitude)
    }

    fun onDestroy() {
        mainView = null
    }
}