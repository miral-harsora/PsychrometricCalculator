package com.application.psychrometriccalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.psychrometriccalculator.model.LocationListItem
import com.application.psychrometriccalculator.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val weatherRepository: WeatherRepository): ViewModel() {
    private var weather = MutableLiveData<LocationListItem>()
     val weatherLiveData : LiveData<LocationListItem> = weather
    private val _relativeHumidity = MutableStateFlow("")
    val relativeHumidity = _relativeHumidity.asStateFlow()

    fun getWeather(apiKey: String, location: String, aqi: String): LiveData<LocationListItem> {
        viewModelScope.launch {
           weather.value =  weatherRepository.getWeather(apiKey, location, aqi).value
        }
        return weatherLiveData
    }

    fun updateRelativeHumidity(humidity: String) {
        viewModelScope.launch {
            _relativeHumidity.value = humidity
        }
    }
}