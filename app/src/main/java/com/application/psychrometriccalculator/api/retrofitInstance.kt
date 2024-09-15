package com.application.psychrometriccalculator.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitInstance {
    val instance = Retrofit.Builder().baseUrl("https://api.weatherapi.com/v1/").addConverterFactory(
        GsonConverterFactory.create()).build()
}