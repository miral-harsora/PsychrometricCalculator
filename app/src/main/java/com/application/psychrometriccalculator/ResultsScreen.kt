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

@Composable
fun ResultsScreen(mainViewModel: MainViewModel, param: String?, param2: String?, param3: String?) {
    var results_label = listOf("Saturated Pressure","Vapour Pressure","Absolute Pressure",
        "Wet-Bulb Temperature","Dew-Point Temperature","Specific Humidity","Specific Humidity at Saturation Pressure",
        "Degree of Saturation","Density of Dry Air","Density of Water Vapour",
        "Density of Moist Air","Specific Volume of UnSaturated Air",
        "Specific Volume of Saturated Air","Enthalpy of Vapour",
        "Enthalpy of Liquid","Enthalpy of Evapouration","Enthalpy of Dry Air",
        "Enthalpy of Water Vapour","Enthalpy of Mixture")
    var results = listOf(mainViewModel.saturatedPressure.collectAsState().value,
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
        mainViewModel.enthalpyOfMixture.collectAsState().value)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(results_label.size) {
                ResultsItem(results_label.get(index = it),
                    results.get(index = it),param2,param3)
            }
        }
    }
}

@Composable
fun ResultsItem(results_label: String, param: String?, param2: String?, param3: String?) {
    val options = listOf("Option 1", "Option 2", "Option 3")
    var selectedValue by remember { mutableStateOf(options[0]) }

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
                options = options,
                label = "Unit",
                onValueChangedEvent = {selectedValue=it},
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center)
                    .padding(start=10.dp)
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