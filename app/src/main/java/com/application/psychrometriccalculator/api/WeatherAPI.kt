package com.application.psychrometriccalculator.api

import androidx.lifecycle.LiveData
import com.application.psychrometriccalculator.model.LocationListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("current.json")
    suspend fun getWeather(@Query("key") apiKey: String,@Query("q") location: String,@Query("aqi") aqi: String): Response<LocationListItem>
}