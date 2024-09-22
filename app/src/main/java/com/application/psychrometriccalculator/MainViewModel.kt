package com.application.psychrometriccalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.psychrometriccalculator.Constants.C3
import com.application.psychrometriccalculator.Constants.C4
import com.application.psychrometriccalculator.Constants.C5
import com.application.psychrometriccalculator.model.LocationListItem
import com.application.psychrometriccalculator.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class MainViewModel(private val weatherRepository: WeatherRepository): ViewModel() {
    private var weather = MutableLiveData<LocationListItem>()
     val weatherLiveData : LiveData<LocationListItem> = weather
    private val _relativeHumidity = MutableStateFlow("")
    val relativeHumidity = _relativeHumidity.asStateFlow()
    private val _dryBulbTemperature = MutableStateFlow("")
    val dryBulbTemperature = _dryBulbTemperature.asStateFlow()
    private val _barometricPressure = MutableStateFlow("")
    val barometricPressure = _barometricPressure.asStateFlow()
    private val _saturatedPressure = MutableStateFlow("")
    val saturatedPressure = _saturatedPressure.asStateFlow()
    private val _vapourPressure = MutableStateFlow("")
    val vapourPressure = _vapourPressure.asStateFlow()
    private val _absolutePressure = MutableStateFlow("")
    val absolutePressure = _absolutePressure.asStateFlow()
    private val _wetBulbTemperature = MutableStateFlow("")
    val wetBulbTemperature = _wetBulbTemperature.asStateFlow()
    private val _dewPointTemperature = MutableStateFlow("")
    val dewPointTemperature = _dewPointTemperature.asStateFlow()
    private val _specificHumidity = MutableStateFlow("")
    val specificHumidity = _specificHumidity.asStateFlow()
    private val _specificHumidityAtSP = MutableStateFlow("")
    val specificHumidityAtSP = _specificHumidityAtSP.asStateFlow()
    private val _degreeOfSaturation = MutableStateFlow("")
    val degreeOfSaturation = _degreeOfSaturation.asStateFlow()
    private val _densityOfDryAir = MutableStateFlow("")
    val densityOfDryAir = _densityOfDryAir.asStateFlow()
    private val _densityOfWaterVapour = MutableStateFlow("")
    val densityOfWaterVapour = _densityOfWaterVapour.asStateFlow()
    private val _densityOfMoistAir = MutableStateFlow("")
    val densityOfMoistAir = _densityOfMoistAir.asStateFlow()
    private val _specificVolumeOfUnSaturatedAir = MutableStateFlow("")
    val specificVolumeOfUnSaturatedAir = _specificVolumeOfUnSaturatedAir.asStateFlow()
    private val _specificVolumeOfSaturatedAir = MutableStateFlow("")
    val specificVolumeOfSaturatedAir = _specificVolumeOfSaturatedAir.asStateFlow()
    private val _enthalpyOfVapour = MutableStateFlow("")
    val enthalpyOfVapour = _enthalpyOfVapour.asStateFlow()
    private val _enthalpyOfLiquid = MutableStateFlow("")
    val enthalpyOfLiquid = _enthalpyOfLiquid.asStateFlow()
    private val _enthalpyOfEvapouration = MutableStateFlow("")
    val enthalpyOfEvapouration = _enthalpyOfEvapouration.asStateFlow()
    private val _enthalpyOfDryAir = MutableStateFlow("")
    val enthalpyOfDryAir = _enthalpyOfDryAir.asStateFlow()
    private val _enthalpyOfWaterVapour = MutableStateFlow("")
    val enthalpyOfWaterVapour = _enthalpyOfWaterVapour.asStateFlow()
    private val _enthalpyOfMixture = MutableStateFlow("")
    val enthalpyOfMixture = _enthalpyOfMixture.asStateFlow()








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
    fun updateDryBulbTemperature(temperature: String) {
        viewModelScope.launch {
            _dryBulbTemperature.value = temperature
        }
    }
    fun updateBarometricPressure(pressure: String) {
        viewModelScope.launch {
            _barometricPressure.value = pressure
        }
    }

    fun calculateSP(){
        val dryBulbTemperature = _dryBulbTemperature.value.toDouble()+273.15
        _saturatedPressure.value = //((Constants.C1/dryBulbTemperature)/10).toString()
            (Math.pow(2.71828182845904,((Constants.C1/dryBulbTemperature)+(Constants.C2)+(Constants.C3*dryBulbTemperature)+
                   (Constants.C4*(Math.pow(dryBulbTemperature,2.0)))+(Constants.C5*(Math.pow(dryBulbTemperature,3.0)))+
                    (Constants.C6*(Math.log(dryBulbTemperature))))))
                .round(3)
                .toString()


    }
    fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()
    fun calculateVapourPressure(){
        val relativeHumidityF = _relativeHumidity.value.toDouble()/100
        _vapourPressure.value = (_saturatedPressure.value.toDouble()*relativeHumidityF).round(3).toString()


    }

    fun calculateAbsolutePressure(){
        _absolutePressure.value = (barometricPressure.value.toDouble()-vapourPressure.value.toDouble()).round(3).toString()
    }

    fun calculateWetBulbTemperature() {
        val rh = _relativeHumidity.value.toDouble()
        val dbt = _dryBulbTemperature.value.toDouble()
        _wetBulbTemperature.value = (-5.806+0.672*dbt-0.006*dbt*dbt+(0.061+0.004*dbt+0.000099*dbt*dbt)*rh+(-0.000033-0.000005*dbt-0.0000001*dbt*dbt)*rh*rh).round(3).toString()
    }

    fun calculateDewPointTemperature() {
        _dewPointTemperature.value =
            (((4030 * (dryBulbTemperature.value.toDouble()+ 235)) / (4030 - ((dryBulbTemperature.value.toDouble() + 235) * Math.log10(_relativeHumidity.value.toDouble()/100)))) - 235).round(3).toString()
    }

    fun calculateSpecificHumidity() {
        _specificHumidity.value = (0.622*(vapourPressure.value.toDouble()/(barometricPressure.value.toDouble()-vapourPressure.value.toDouble()))).round(3).toString()
    }

    fun calculateSpecificHumidityAtSP(){
        _specificHumidityAtSP.value = (0.622*(saturatedPressure.value.toDouble()/(barometricPressure.value.toDouble()-saturatedPressure.value.toDouble()))).round(3).toString()
    }

    fun calculateDegreeOfSaturation() {
        _degreeOfSaturation.value = ((relativeHumidity.value.toDouble()/100)*((barometricPressure.value.toDouble()-saturatedPressure.value.toDouble())/(barometricPressure.value.toDouble()-vapourPressure.value.toDouble()))).round(3).toString()
    }

    fun calculateDensityOfDryAir() {
        _densityOfDryAir.value = ((absolutePressure.value.toDouble()*1000)/(287.04*(dryBulbTemperature.value.toDouble()+273.5))).round(3).toString()
    }

    fun calculateDensityOfWaterVapour() {
        _densityOfWaterVapour.value = ((vapourPressure.value.toDouble()*1000)/(461.52*(dryBulbTemperature.value.toDouble()+273.5))).round(3).toString()
    }

    fun calculateDensityOfMoistAir() {
        _densityOfMoistAir.value = (densityOfDryAir.value.toDouble()+densityOfWaterVapour.value.toDouble()).round(3).toString()
    }

    fun calculateSpecificVolumeOfUnSaturatedAir(){
        _specificVolumeOfUnSaturatedAir.value = ((287.04*(dryBulbTemperature.value.toDouble()+273.15))/((barometricPressure.value.toDouble()-vapourPressure.value.toDouble())*1000)).round(3).toString()
    }

    fun calculateSpecificVolumeOfSaturatedAir(){
        _specificVolumeOfSaturatedAir.value = ((287.04*(dryBulbTemperature.value.toDouble()+273.15))/((barometricPressure.value.toDouble()-saturatedPressure.value.toDouble())*1000)).round(3).toString()
    }

    fun calculateEnthalpyOfVapour(){
       _enthalpyOfVapour.value = (2501 + (1.88 * dryBulbTemperature.value.toDouble())).round(3).toString()
    }

    fun calculateEnthalpyOfLiquid(){
        _enthalpyOfLiquid.value = (4.18 * dryBulbTemperature.value.toDouble()).round(3).toString()
    }

    fun calculateEnthalpyOfEvapouration(){
        _enthalpyOfEvapouration.value = (enthalpyOfVapour.value.toDouble()-enthalpyOfLiquid.value.toDouble()).round(3).toString()
    }

    fun calculateEnthalpyOfDryAir(){
        _enthalpyOfDryAir.value = (1.005*dryBulbTemperature.value.toDouble()).round(3).toString()
    }

    fun calculateEnthalpyOfWaterVapour() {
        _enthalpyOfWaterVapour.value = (specificHumidity.value.toDouble()*enthalpyOfVapour.value.toDouble()).round(3).toString()
    }

    fun calculateEnthalpyOfMixture(){
        _enthalpyOfMixture.value = (enthalpyOfDryAir.value.toDouble()+enthalpyOfWaterVapour.value.toDouble()).round(3).toString()
    }

}