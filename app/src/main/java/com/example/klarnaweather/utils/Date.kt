package com.example.klarnaweather.utils


import java.util.Date


object Date {

    /**
     * Convert unix time to Date string
     * @param epochTime
     */
    fun formatDate(epochTime: Long): String = Date(epochTime * 1000L).toString()


}