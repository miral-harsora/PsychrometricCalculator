package com.application.psychrometriccalculator.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.psychrometriccalculator.api.WeatherAPI
import com.application.psychrometriccalculator.model.LocationListItem

class WeatherRepository(private val weatherAPI: WeatherAPI) {

    private val weather = MutableLiveData<LocationListItem>()
    private var weatherLiveData : LiveData<LocationListItem> = weather

    suspend fun getWeather(apiKey: String, location: String, aqi: String): LiveData<LocationListItem> {
       weather.value= weatherAPI.getWeather(apiKey, location, aqi).body()
       return weatherLiveData
    }


}