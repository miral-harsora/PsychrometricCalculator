package com.application.psychrometriccalculator

import android.app.Application
import com.application.psychrometriccalculator.api.WeatherAPI
import com.application.psychrometriccalculator.api.retrofitInstance
import com.application.psychrometriccalculator.repository.WeatherRepository

class PsychometricApp: Application() {
    lateinit var weatherRepository: WeatherRepository
    override fun onCreate() {
        super.onCreate()
        weatherRepository = WeatherRepository(retrofitInstance.instance.create(WeatherAPI::class.java))
    }
}