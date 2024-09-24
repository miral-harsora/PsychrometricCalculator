package com.application.psychrometriccalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.psychrometriccalculator.Constants.round
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
    private val _dryBulbTemperature = MutableStateFlow("")
    val dryBulbTemperature = _dryBulbTemperature.asStateFlow()
    private val _barometricPressure = MutableStateFlow("")
    val barometricPressure = _barometricPressure.asStateFlow()
    val _saturatedPressure = MutableStateFlow("")
    var saturatedPressure = _saturatedPressure.asStateFlow()
    var saturatedPressureC = MutableStateFlow("")
    internal val _vapourPressure = MutableStateFlow("")
    val vapourPressure = _vapourPressure.asStateFlow()
    var vapourPressureC = MutableStateFlow("")
    internal val _absolutePressure = MutableStateFlow("")
    val absolutePressure = _absolutePressure.asStateFlow()
    var absolutePressureC = MutableStateFlow("")
    internal val _wetBulbTemperature = MutableStateFlow("")
    val wetBulbTemperature = _wetBulbTemperature.asStateFlow()
    var wetBulbTemperatureC = MutableStateFlow("")
    internal val _dewPointTemperature = MutableStateFlow("")
    val dewPointTemperature = _dewPointTemperature.asStateFlow()
    var dewPointTemperatureC = MutableStateFlow("")
    internal val _specificHumidity = MutableStateFlow("")
    val specificHumidity = _specificHumidity.asStateFlow()
    var specificHumidityC = MutableStateFlow("")
    internal val _specificHumidityAtSP = MutableStateFlow("")
    val specificHumidityAtSP = _specificHumidityAtSP.asStateFlow()
    var specificHumidityAtSPC = MutableStateFlow("")
    private val _degreeOfSaturation = MutableStateFlow("")
    val degreeOfSaturation = _degreeOfSaturation.asStateFlow()
    var degreeOfSaturationC = MutableStateFlow("")
    val _densityOfDryAir = MutableStateFlow("")
    val densityOfDryAir = _densityOfDryAir.asStateFlow()
    var densityOfDryAirC = MutableStateFlow("")
    internal val _densityOfWaterVapour = MutableStateFlow("")
    val densityOfWaterVapour = _densityOfWaterVapour.asStateFlow()
    var densityOfWaterVapourC = MutableStateFlow("")
    internal val _densityOfMoistAir = MutableStateFlow("")
    val densityOfMoistAir = _densityOfMoistAir.asStateFlow()
    var densityOfMoistAirC = MutableStateFlow("")
    internal val _specificVolumeOfUnSaturatedAir = MutableStateFlow("")
    val specificVolumeOfUnSaturatedAir = _specificVolumeOfUnSaturatedAir.asStateFlow()
    var specificVolumeOfUnSaturatedAirC = MutableStateFlow("")
    internal val _specificVolumeOfSaturatedAir = MutableStateFlow("")
    val specificVolumeOfSaturatedAir = _specificVolumeOfSaturatedAir.asStateFlow()
    var specificVolumeOfSaturatedAirC = MutableStateFlow("")
    internal val _enthalpyOfVapour = MutableStateFlow("")
    val enthalpyOfVapour = _enthalpyOfVapour.asStateFlow()
    var enthalpyOfVapourC = MutableStateFlow("")
    internal val _enthalpyOfLiquid = MutableStateFlow("")
    val enthalpyOfLiquid = _enthalpyOfLiquid.asStateFlow()
    var enthalpyOfLiquidC = MutableStateFlow("")
    internal val _enthalpyOfEvapouration = MutableStateFlow("")
    val enthalpyOfEvapouration = _enthalpyOfEvapouration.asStateFlow()
    var enthalpyOfEvapourationC = MutableStateFlow("")
    internal val _enthalpyOfDryAir = MutableStateFlow("")
    val enthalpyOfDryAir = _enthalpyOfDryAir.asStateFlow()
    var enthalpyOfDryAirC = MutableStateFlow("")
    internal val _enthalpyOfWaterVapour = MutableStateFlow("")
    val enthalpyOfWaterVapour = _enthalpyOfWaterVapour.asStateFlow()
    var enthalpyOfWaterVapourC = MutableStateFlow("")
    internal val _enthalpyOfMixture = MutableStateFlow("")
    val enthalpyOfMixture = _enthalpyOfMixture.asStateFlow()
    var enthalpyOfMixtureC = MutableStateFlow("")







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
        saturatedPressureC.value = _saturatedPressure.value


    }

    fun calculateVapourPressure(){
        val relativeHumidityF = _relativeHumidity.value.toDouble()/100
        _vapourPressure.value = (_saturatedPressure.value.toDouble()*relativeHumidityF).round(3).toString()
        vapourPressureC.value = _vapourPressure.value

    }

    fun calculateAbsolutePressure(){
        _absolutePressure.value = (barometricPressure.value.toDouble()-vapourPressure.value.toDouble()).round(3).toString()
        absolutePressureC.value = absolutePressure.value
    }

    fun calculateWetBulbTemperature() {
        val rh = _relativeHumidity.value.toDouble()
        val dbt = _dryBulbTemperature.value.toDouble()
        _wetBulbTemperature.value = (-5.806+0.672*dbt-0.006*dbt*dbt+(0.061+0.004*dbt+0.000099*dbt*dbt)*rh+(-0.000033-0.000005*dbt-0.0000001*dbt*dbt)*rh*rh).round(3).toString()
        wetBulbTemperatureC.value = wetBulbTemperature.value
    }

    fun calculateDewPointTemperature() {
        _dewPointTemperature.value =
            (((4030 * (dryBulbTemperature.value.toDouble()+ 235)) / (4030 - ((dryBulbTemperature.value.toDouble() + 235) * Math.log10(_relativeHumidity.value.toDouble()/100)))) - 235).round(3).toString()
        dewPointTemperatureC.value = wetBulbTemperature.value
    }

    fun calculateSpecificHumidity() {
        _specificHumidity.value = (0.622*(vapourPressure.value.toDouble()/(barometricPressure.value.toDouble()-vapourPressure.value.toDouble()))).round(3).toString()
        specificHumidityC.value = specificHumidity.value
    }

    fun calculateSpecificHumidityAtSP(){
        _specificHumidityAtSP.value = (0.622*(saturatedPressure.value.toDouble()/(barometricPressure.value.toDouble()-saturatedPressure.value.toDouble()))).round(3).toString()
        specificHumidityAtSPC.value = specificHumidityAtSP.value
    }

    fun calculateDegreeOfSaturation() {
        _degreeOfSaturation.value = ((relativeHumidity.value.toDouble()/100)*((barometricPressure.value.toDouble()-saturatedPressure.value.toDouble())/(barometricPressure.value.toDouble()-vapourPressure.value.toDouble()))).round(3).toString()
        degreeOfSaturationC.value = degreeOfSaturation.value
    }

    fun calculateDensityOfDryAir() {
        _densityOfDryAir.value = ((absolutePressure.value.toDouble()*1000)/(287.04*(dryBulbTemperature.value.toDouble()+273.5))).round(3).toString()
        densityOfDryAirC.value = densityOfDryAir.value

    }

    fun calculateDensityOfWaterVapour() {
        _densityOfWaterVapour.value = ((vapourPressure.value.toDouble()*1000)/(461.52*(dryBulbTemperature.value.toDouble()+273.5))).round(3).toString()
        densityOfWaterVapourC.value = densityOfWaterVapour.value

    }

    fun calculateDensityOfMoistAir() {
        _densityOfMoistAir.value = (densityOfDryAir.value.toDouble()+densityOfWaterVapour.value.toDouble()).round(3).toString()
        densityOfMoistAirC.value = densityOfMoistAir.value
    }

    fun calculateSpecificVolumeOfUnSaturatedAir(){
        _specificVolumeOfUnSaturatedAir.value = ((287.04*(dryBulbTemperature.value.toDouble()+273.15))/((barometricPressure.value.toDouble()-vapourPressure.value.toDouble())*1000)).round(3).toString()
        specificVolumeOfUnSaturatedAirC.value = specificVolumeOfUnSaturatedAir.value
    }

    fun calculateSpecificVolumeOfSaturatedAir(){
        _specificVolumeOfSaturatedAir.value = ((287.04*(dryBulbTemperature.value.toDouble()+273.15))/((barometricPressure.value.toDouble()-saturatedPressure.value.toDouble())*1000)).round(3).toString()
        specificVolumeOfSaturatedAirC.value = specificVolumeOfSaturatedAir.value

    }

    fun calculateEnthalpyOfVapour(){
       _enthalpyOfVapour.value = (2501 + (1.88 * dryBulbTemperature.value.toDouble())).round(3).toString()
        enthalpyOfVapourC.value = enthalpyOfVapour.value
    }

    fun calculateEnthalpyOfLiquid(){
        _enthalpyOfLiquid.value = (4.18 * dryBulbTemperature.value.toDouble()).round(3).toString()
        enthalpyOfLiquidC.value = enthalpyOfLiquid.value
    }

    fun calculateEnthalpyOfEvapouration(){
        _enthalpyOfEvapouration.value = (enthalpyOfVapour.value.toDouble()-enthalpyOfLiquid.value.toDouble()).round(3).toString()
        enthalpyOfEvapourationC.value = enthalpyOfEvapouration.value

    }

    fun calculateEnthalpyOfDryAir(){
        _enthalpyOfDryAir.value = (1.005*dryBulbTemperature.value.toDouble()).round(3).toString()
        enthalpyOfDryAirC.value = enthalpyOfDryAir.value

    }

    fun calculateEnthalpyOfWaterVapour() {
        _enthalpyOfWaterVapour.value = (specificHumidity.value.toDouble()*enthalpyOfVapour.value.toDouble()).round(3).toString()
        enthalpyOfWaterVapourC.value = enthalpyOfWaterVapour.value

    }

    fun calculateEnthalpyOfMixture(){
        _enthalpyOfMixture.value = (enthalpyOfDryAir.value.toDouble()+enthalpyOfWaterVapour.value.toDouble()).round(3).toString()
        enthalpyOfMixtureC.value = enthalpyOfMixture.value

    }

}