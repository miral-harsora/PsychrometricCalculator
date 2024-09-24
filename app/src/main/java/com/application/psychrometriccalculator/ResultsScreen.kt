package com.application.psychrometriccalculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.psychrometriccalculator.Constants.round

@Composable
fun ResultsScreen(mainViewModel: MainViewModel, param: String?, param2: String?, param3: String?) {
    val results_label = listOf(
        "Saturated Pressure",
        "Vapour Pressure",
        "Absolute Pressure",
        "Wet-Bulb Temperature",
        "Dew-Point Temperature",
        "Specific Humidity",
        "Specific Humidity at Saturation Pressure",
        "Degree of Saturation",
        "Density of Dry Air",
        "Density of Water Vapour",
        "Density of Moist Air",
        "Specific Volume of UnSaturated Air",
        "Specific Volume of Saturated Air",
        "Enthalpy of Vapour",
        "Enthalpy of Liquid",
        "Enthalpy of Evapouration",
        "Enthalpy of Dry Air",
        "Enthalpy of Water Vapour",
        "Enthalpy of Mixture"
    )
    val results = listOf(
        mainViewModel.saturatedPressure.collectAsState().value,
        mainViewModel.vapourPressure.collectAsState().value,
        mainViewModel.absolutePressure.collectAsState().value,
        mainViewModel.wetBulbTemperature.collectAsState().value,
        mainViewModel.dewPointTemperature.collectAsState().value,
        mainViewModel.specificHumidity.collectAsState().value,
        mainViewModel.specificHumidityAtSP.collectAsState().value,
        mainViewModel.degreeOfSaturation.collectAsState().value,
        mainViewModel.densityOfDryAir.collectAsState().value,
        mainViewModel.densityOfWaterVapour.collectAsState().value,
        mainViewModel.densityOfMoistAir.collectAsState().value,
        mainViewModel.specificVolumeOfUnSaturatedAir.collectAsState().value,
        mainViewModel.specificVolumeOfSaturatedAir.collectAsState().value,
        mainViewModel.enthalpyOfVapour.collectAsState().value,
        mainViewModel.enthalpyOfLiquid.collectAsState().value,
        mainViewModel.enthalpyOfEvapouration.collectAsState().value,
        mainViewModel.enthalpyOfDryAir.collectAsState().value,
        mainViewModel.enthalpyOfWaterVapour.collectAsState().value,
        mainViewModel.enthalpyOfMixture.collectAsState().value
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(results_label.size) {
                ResultsItem(
                    results_label.get(index = it),
                    results.get(index = it), mainViewModel, param3
                )
            }
        }
    }
}

@Composable
fun ResultsItem(
    results_label: String,
    param: String?,
    mainViewModel: MainViewModel,
    param3: String?
) {
    val pressure_options = listOf("kpa", "psi", "bar", "mmWC", "inWC")
    val temp_options = listOf("°C", "°F")
    val humidity_options = listOf("kg/kg", "lb/lb")
    val degree_options = listOf("%")
    val density_options = listOf("kg/m³", "lb/ft³")
    val specific_volume_options = listOf("m³/kg", "ft³/lb")
    val enthalpy_options = listOf("kJ/kg", "BTU/lb")
    var selectedValue by remember {
        mutableStateOf(
            (if (results_label.contains("pressure")) {
                pressure_options[0]
            } else if (results_label.contains("temperature", true)) {
                temp_options[0]
            } else if (results_label.contains("humidity", true)) {
                humidity_options[0]
            } else if (results_label.contains("degree", true)) {
                degree_options[0]
            } else if (results_label.contains("density", true)) {
                density_options[0]
            } else if (results_label.contains("specific volume", true)) {
                specific_volume_options[0]
            } else if (results_label.contains("enthalpy", true)) {
                enthalpy_options[0]
            } else {
                pressure_options[0]
            })
        )
    }

    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = results_label, fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.CenterStart)
            )
            Text(
                text = param.plus(""), fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center)
            )
            DynamicSelectTextField(
                selectedValue = selectedValue,
                options = (if (results_label.contains("pressure", true)) {
                    pressure_options
                } else if (results_label.contains("temperature", true)) {
                    temp_options
                } else if (results_label.contains("humidity", true)) {
                    humidity_options
                } else if (results_label.contains("degree", true)) {
                    degree_options
                } else if (results_label.contains("density", true)) {
                    density_options
                } else if (results_label.contains("specific volume", true)) {
                    specific_volume_options
                } else if (results_label.contains("enthalpy", true)) {
                    enthalpy_options
                } else {
                    pressure_options
                }) as List<String>,
                label = "Unit",
                onValueChangedEvent = {
                    if (results_label == "Saturated Pressure") {
                        when (it) {
                            "psi" -> {
                                mainViewModel._saturatedPressure.value =
                                    (mainViewModel.saturatedPressureC.value.toDouble() * 0.145038).round(
                                        3
                                    ).toString()
                            }

                            "bar" -> {
                                mainViewModel._saturatedPressure.value =
                                    (mainViewModel.saturatedPressureC.value.toDouble() * 0.01).round(
                                        3
                                    ).toString()
                            }

                            "mmWC" -> {
                                mainViewModel._saturatedPressure.value =
                                    (mainViewModel.saturatedPressureC.value.toDouble() * 101.97162129779).round(
                                        3
                                    ).toString()
                            }

                            "inWC" -> {
                                mainViewModel._saturatedPressure.value =
                                    (mainViewModel.saturatedPressureC.value.toDouble() * 4.0146307866177).round(
                                        3
                                    ).toString()
                            }

                            "kpa" -> {
                                mainViewModel._saturatedPressure.value =
                                    (mainViewModel.saturatedPressureC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }
                    } else if (results_label == "Vapour Pressure") {
                        when (it) {
                            "psi" -> {
                                mainViewModel._vapourPressure.value =
                                    (mainViewModel.vapourPressureC.value.toDouble() * 0.145038).round(
                                        3
                                    ).toString()
                            }

                            "bar" -> {
                                mainViewModel._vapourPressure.value =
                                    (mainViewModel.vapourPressureC.value.toDouble() * 0.01).round(3)
                                        .toString()
                            }

                            "mmWC" -> {
                                mainViewModel._vapourPressure.value =
                                    (mainViewModel.vapourPressureC.value.toDouble() * 101.97162129779).round(
                                        3
                                    ).toString()
                            }

                            "inWC" -> {
                                mainViewModel._vapourPressure.value =
                                    (mainViewModel.vapourPressureC.value.toDouble() * 4.0146307866177).round(
                                        3
                                    ).toString()
                            }

                            "kpa" -> {
                                mainViewModel._vapourPressure.value =
                                    (mainViewModel.vapourPressureC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }
                    } else if (results_label == "Absolute Pressure") {
                        when (it) {
                            "psi" -> {
                                mainViewModel._absolutePressure.value =
                                    (mainViewModel.absolutePressureC.value.toDouble() * 0.145038).round(
                                        3
                                    ).toString()
                            }

                            "bar" -> {
                                mainViewModel._absolutePressure.value =
                                    (mainViewModel.absolutePressureC.value.toDouble() * 0.01).round(
                                        3
                                    ).toString()
                            }

                            "mmWC" -> {
                                mainViewModel._absolutePressure.value =
                                    (mainViewModel.absolutePressureC.value.toDouble() * 101.97162129779).round(
                                        3
                                    ).toString()
                            }

                            "inWC" -> {
                                mainViewModel._absolutePressure.value =
                                    (mainViewModel.absolutePressureC.value.toDouble() * 4.0146307866177).round(
                                        3
                                    ).toString()
                            }

                            "kpa" -> {
                                mainViewModel._absolutePressure.value =
                                    (mainViewModel.absolutePressureC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }
                    } else if (results_label == "Wet-Bulb Temperature") {
                        when (it) {
                            "°C" -> {
                                mainViewModel._wetBulbTemperature.value =
                                    (mainViewModel.wetBulbTemperatureC.value.toDouble()).round(3)
                                        .toString()
                            }

                            "°F" -> {
                                mainViewModel._wetBulbTemperature.value =
                                    (mainViewModel.wetBulbTemperatureC.value.toDouble() * 1.8 + 32).round(
                                        3
                                    ).toString()
                            }
                        }

                    } else if (results_label == "Dew-Point Temperature") {
                        when (it) {
                            "°C" -> {
                                mainViewModel._dewPointTemperature.value =
                                    (mainViewModel.dewPointTemperatureC.value.toDouble()).round(3)
                                        .toString()
                            }

                            "°F" -> {
                                mainViewModel._dewPointTemperature.value =
                                    (mainViewModel.dewPointTemperatureC.value.toDouble() * 1.8 + 32).round(
                                        3
                                    ).toString()
                            }
                        }

                    } else if (results_label == "Specific Humidity") {
                        when (it) {
                            "lb/lb" -> {
                                mainViewModel._specificHumidity.value =
                                    (mainViewModel.specificHumidityC.value.toDouble() * 2.2046226218).round(
                                        3
                                    ).toString()
                            }

                            "kg/kg" -> {
                                mainViewModel._specificHumidity.value =
                                    (mainViewModel.specificHumidityC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }
                    } else if (results_label == "Specific Humidity at Saturation Pressure") {
                        when (it) {
                            "lb/lb" -> {
                                mainViewModel._specificHumidityAtSP.value =
                                    (mainViewModel.specificHumidityAtSPC.value.toDouble() * 2.2046226218).round(
                                        3
                                    ).toString()
                            }

                            "kg/kg" -> {
                                mainViewModel._specificHumidityAtSP.value =
                                    (mainViewModel.specificHumidityAtSPC.value.toDouble()).round(3)
                                        .toString()

                            }
                        }

                    } else if (results_label == "Degree of Saturation") {

                    } else if (results_label == "Density of Dry Air") {
                        when (it) {
                            "lb/ft³" -> {
                                mainViewModel._densityOfDryAir.value =
                                    (mainViewModel.densityOfDryAirC.value.toDouble() * 0.062428).round(
                                        3
                                    ).toString()
                            }

                            "kg/m³" -> {
                                mainViewModel._densityOfDryAir.value =
                                    (mainViewModel.densityOfDryAirC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }
                    } else if (results_label == "Density of Water Vapour") {
                        when (it) {
                            "lb/ft³" -> {
                                mainViewModel._densityOfWaterVapour.value =
                                    (mainViewModel.densityOfWaterVapourC.value.toDouble() * 0.062428).round(
                                        3
                                    ).toString()
                            }

                            "kg/m³" -> {
                                mainViewModel._densityOfWaterVapour.value =
                                    (mainViewModel.densityOfWaterVapourC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }
                    } else if (results_label == "Density of Moist Air") {
                        when (it) {
                            "lb/ft³" -> {
                                mainViewModel._densityOfMoistAir.value =
                                    (mainViewModel.densityOfMoistAirC.value.toDouble() * 0.062428).round(
                                        3
                                    ).toString()
                            }

                            "kg/m³" -> {
                                mainViewModel._densityOfMoistAir.value =
                                    (mainViewModel.densityOfMoistAirC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }
                    } else if (results_label == "Specific Volume of UnSaturated Air") {
                        when (it) {
                            "m³/kg" -> {
                                mainViewModel._specificVolumeOfUnSaturatedAir.value =
                                    (mainViewModel.specificVolumeOfUnSaturatedAirC.value.toDouble() * 16.0185).round(
                                        3
                                    ).toString()
                            }

                            "ft³/lb" -> {
                                mainViewModel._specificVolumeOfUnSaturatedAir.value =
                                    (mainViewModel.specificVolumeOfUnSaturatedAirC.value.toDouble()).round(
                                        3
                                    ).toString()
                            }
                        }

                    } else if (results_label == "Specific Volume of Saturated Air") {
                        when (it) {
                            "m³/kg" -> {
                                mainViewModel._specificVolumeOfSaturatedAir.value =
                                    (mainViewModel.specificVolumeOfSaturatedAirC.value.toDouble() * 16.0185).round(
                                        3
                                    ).toString()
                            }

                            "ft³/lb" -> {
                                mainViewModel._specificVolumeOfSaturatedAir.value =
                                    (mainViewModel.specificVolumeOfSaturatedAirC.value.toDouble()).round(
                                        3
                                    ).toString()
                            }

                        }
                    } else if (results_label == "Enthalpy of Vapour") {
                        when (it) {
                            "BTU/lb" -> {
                                mainViewModel._enthalpyOfVapour.value =
                                    (mainViewModel.enthalpyOfVapourC.value.toDouble() * 0.429923).round(
                                        3
                                    ).toString()
                            }
                            "kJ/kg" -> {
                                mainViewModel._enthalpyOfVapour.value =
                                    (mainViewModel.enthalpyOfVapourC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }

                    } else if (results_label == "Enthalpy of Liquid") {
                        when (it) {
                            "BTU/lb" -> {
                                mainViewModel._enthalpyOfLiquid.value =
                                    (mainViewModel.enthalpyOfLiquidC.value.toDouble() * 0.429923).round(
                                        3
                                    ).toString()
                            }
                            "kJ/kg" -> {
                                mainViewModel._enthalpyOfLiquid.value =
                                    (mainViewModel.enthalpyOfLiquidC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }

                    } else if(results_label == "Enthalpy of Evapouration"){
                        when (it){
                            "BTU/lb" -> {
                                mainViewModel._enthalpyOfEvapouration.value =
                                    (mainViewModel.enthalpyOfEvapourationC.value.toDouble() * 0.429923).round(
                                        3
                                    ).toString()
                            }
                            "kJ/kg" -> {
                                mainViewModel._enthalpyOfEvapouration.value =
                                    (mainViewModel.enthalpyOfEvapourationC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }
                    } else if(results_label == "Enthalpy of Dry Air"){
                        when (it){
                            "BTU/lb" -> {
                                mainViewModel._enthalpyOfDryAir.value =
                                    (mainViewModel.enthalpyOfDryAirC.value.toDouble() * 0.49923).round(3).toString()
                            }
                            "kJ/kg" -> {
                                mainViewModel._enthalpyOfDryAir.value =
                                    (mainViewModel.enthalpyOfDryAirC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }
                    }
                    else if(results_label == "Enthalpy of Water Vapour"){
                        when (it){
                            "BTU/lb" -> {
                                mainViewModel._enthalpyOfWaterVapour.value =
                                    (mainViewModel.enthalpyOfWaterVapourC.value.toDouble() * 0.49923).round(3).toString()
                            }
                            "kJ/kg" -> {
                                mainViewModel._enthalpyOfWaterVapour.value =
                                    (mainViewModel.enthalpyOfWaterVapourC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }
                    }
                    else if(results_label == "Enthalpy of Mixture"){
                        when (it){
                            "BTU/lb" -> {
                                mainViewModel._enthalpyOfMixture.value =
                                    (mainViewModel.enthalpyOfMixtureC.value.toDouble() * 0.49923).round(3).toString()
                            }
                            "kJ/kg" -> {
                                mainViewModel._enthalpyOfMixture.value =
                                    (mainViewModel.enthalpyOfMixtureC.value.toDouble()).round(3)
                                        .toString()
                            }
                        }
                    }
                    selectedValue = it
                },
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center)
                    .padding(start = 10.dp)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    selectedValue: String,
    options: List<String>,
    label: String,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedValue, onValueChange = {}, readOnly = true,
            label = { Text(text = label, fontSize = 16.sp) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(text = { Text(text = it, fontSize = 16.sp) },
                    onClick = {
                        onValueChangedEvent(it)
                        expanded = false
                    })
            }
        }

    }

}