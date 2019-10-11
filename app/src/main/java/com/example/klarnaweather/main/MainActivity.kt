package com.example.klarnaweather.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.klarnaweather.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


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
        if (checkPermissions()) {
            getLastKnownLocation()
        } else {
            requestPermissions()
        }
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
                Log.i(TAG, "User interaction has been cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation()
            } else {
                presenter.onErrorShown(getString(R.string.permission_denied))
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Get last known location if permissions were granted.
     * Location task can be null.
     */
    private fun getLastKnownLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if(location == null){
                presenter.onErrorShown(getString(R.string.location_error))
            }else{
                presenter.getWeatherInfo(location.latitude, location.longitude)
            }
        }
    }

    /**
     * Check if ACCESS_FINE_LOCATION permission is granted.
     */
    private fun checkPermissions(): Boolean =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    /**
     * Request ACCESS_FINE_LOCATION permissions without
     * any rationale message.
     */
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }


}
