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



class MainActivity : AppCompatActivity(), MainView {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val presenter = MainPresenter(this)


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

        presenter.permissionResult(requestCode, grantResults, PackageManager.PERMISSION_GRANTED)


    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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


    override fun getLastKnownLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let { presenter.getWeatherInfo(location.latitude, location.longitude) }
        }
    }

    override fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }

    fun onRefreshClick(view: View) {
        requestLocation()
    }

    private fun requestLocation() {
        //check if permission is granted already
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        presenter.checkPermissionIsGranted(isPermissionGranted)

    }


}
