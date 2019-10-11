package com.example.klarnaweather.utils

import android.util.Log
import com.example.klarnaweather.main.TAG
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import java.util.Date


object Date {

    /**
     * Convert unix time to Date string
     * @param epochTime
     */
    fun formatDate(epochTime: Long): String = Date(epochTime * 1000L).toString()


}