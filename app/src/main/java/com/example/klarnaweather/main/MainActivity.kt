package com.example.klarnaweather.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.klarnaweather.R
import com.example.klarnaweather.data.models.Weather
import com.example.klarnaweather.utils.Date
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*


const val REQUEST_CODE = 1
const val TAG = "Location"

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val presenter = MainPresenter(this, MainInteractor())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    override fun onStart() {
        super.onStart()
        requestLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isEmpty()) {
                hideLoading()
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation()
            } else {
                hideLoading()
                presenter.onErrorShown(getString(R.string.permission_denied))
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        reload_iv.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        reload_iv.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun updateWeatherInfo(weather: Weather) {
        location_tv.text = weather.timezone
        date_tv.text = Date.formatDate(weather.currentWeather.time)
        current_temp_tv.text = "${weather.currentWeather.temperature}°F"
        feels_like_tv.text = "Feels Like ${weather.currentWeather.apparentTemperature}°F"
        summary_tv.text = weather.currentWeather.summary
    }

    fun onRefreshClick(view: View) {
        requestLocation()
    }

    /**
     * Checks if location permission is granted and gets the
     * latest location.
     */
    private fun requestLocation() {
        //check if permission is granted already
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (isPermissionGranted) {
            getLastKnownLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
        }
    }

    /**
     * Get last known location. Location task can be null.
     */
    private fun getLastKnownLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location == null) {
                showToast(getString(R.string.location_error))
            } else {
                presenter.getWeatherInfo(location.latitude, location.longitude)
            }
        }
    }

}
