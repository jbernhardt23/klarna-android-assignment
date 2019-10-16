package com.example.klarnaweather

import com.example.klarnaweather.data.WeatherService
import com.example.klarnaweather.data.models.Weather
import com.example.klarnaweather.main.MainPresenter
import com.example.klarnaweather.main.MainView
import com.example.klarnaweather.main.PERMISSION_DENIED_MESSAGE
import com.example.klarnaweather.main.REQUEST_CODE
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    private lateinit var mockView: MainView
    private lateinit var mainPresenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainPresenter = MainPresenter(mockView)
    }

    @Test
    fun testLocationPermissionIsAlreadyGranted() {
        mainPresenter.checkPermissionIsGranted(true)
        verify(mockView).getLastKnownLocation()

    }

    @Test
    fun testLocationPermissionIsNotGranted() {
        mainPresenter.checkPermissionIsGranted(false)
        verify(mockView).requestLocationPermission()
    }

    @Test
    fun testPermissionResultGranted() {
        mainPresenter.permissionResult(REQUEST_CODE, intArrayOf(0), 0)
        verify(mockView).getLastKnownLocation()

    }

    @Test
    fun testPermissionResultNotGranted() {
        mainPresenter.permissionResult(REQUEST_CODE, intArrayOf(0), 1)
        verify(mockView).hideLoading()
        verify(mockView).showToast(PERMISSION_DENIED_MESSAGE)
    }


}
