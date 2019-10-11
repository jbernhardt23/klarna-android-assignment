package com.example.klarnaweather.main

interface MainView {

    /**
     * show snackbar with custom message.
     * @param message
     */
    fun showToast(message: String)
}