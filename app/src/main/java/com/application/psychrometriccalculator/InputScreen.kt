package com.application.psychrometriccalculator

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun InputScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    var city by remember { mutableStateOf("") }
    val search = remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)) {
        GetSearchBar(
            city = city,
            updatedCity = { city = it },navController,mainViewModel,search)
    InputLayout(modifier = Modifier,navController,mainViewModel,search)
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetSearchBar(
    city: String,
    updatedCity: (String) -> Unit,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    search: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 40.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SearchBar (
                query = city,
                onQueryChange = { updatedCity(it) },
                placeholder = { Text("Search Location", fontSize = 18.sp) },
                modifier = Modifier.weight(1.25f).height(60.dp).border(1.dp, Color.Black, RoundedCornerShape(32.dp)),
                colors = SearchBarDefaults.colors( containerColor = Color.Transparent,
                    dividerColor = Color.Transparent,
                    inputFieldColors = SearchBarDefaults.inputFieldColors(
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black)),
                onSearch = {
                    search.value = true
                    mainViewModel.getWeather(BuildConfig.API_KEY, city, "no")
                },
                active = false,
                onActiveChange = {},
                content = {},

            )

            /*  OutlinedTextField(
                  value = city,
                  onValueChange = {
                      city(it)
                  },
                  label = {
                      Text("Search Location") },
                  modifier = Modifier.weight(1.25f)
              )*/

            IconButton(modifier = Modifier
                .weight(0.15f)
                .size(25.dp)
                .align(Alignment.CenterVertically), onClick = {
                    search.value = true
                mainViewModel.getWeather(BuildConfig.API_KEY, city, "no")
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            }


        }
        InputLayout(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            mainViewModel = mainViewModel,
            search = search
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InputLayout(
    modifier: Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    search: MutableState<Boolean>
) {
    val relativeHumidity by mainViewModel.relativeHumidity.collectAsState("")
    val location by mainViewModel.weatherLiveData.observeAsState()
    val dryBulbTemperature by mainViewModel.dryBulbTemperature.collectAsState("")
    val barometricPressure by mainViewModel.barometricPressure.collectAsState("")



    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = relativeHumidity,
                onValueChange = {
                newText ->

                        mainViewModel.updateRelativeHumidity(newText)
                        Log.d("Relative Humidity", newText)

                },
                shape = RoundedCornerShape(28.dp),
                label = { Text("Relative Humidity (%)", fontSize = 18.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 8.dp, end = 8.dp),
                singleLine = true
            )
            OutlinedTextField(
                value = dryBulbTemperature,
                onValueChange = {   mainViewModel.updateDryBulbTemperature(it) },
                shape = RoundedCornerShape(28.dp),
                label = { Text("Dry Bulb Temperature (°C)", fontSize = 18.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 8.dp, end = 8.dp),
                singleLine = true
            )
            OutlinedTextField(
                value = barometricPressure,
                onValueChange = { mainViewModel.updateBarometricPressure(it) },
                shape = RoundedCornerShape(28.dp),
                label = { Text("Barometric Pressure (kPa)", fontSize = 18.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 8.dp, end = 8.dp),
                singleLine = true,
                readOnly = false
            )


        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.8f)
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .height(90.dp)
                    .weight(1f)
                    .padding(16.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    Color(
                        0xFF192BC2
                    )
                )
            ) {
                Text(text = "Reset", fontSize = 20.sp)
            }
            Button(
                onClick = {
                    mainViewModel.calculateSP()
                    mainViewModel.calculateVapourPressure()
                    mainViewModel.calculateAbsolutePressure()
                    mainViewModel.calculateWetBulbTemperature()
                    mainViewModel.calculateDewPointTemperature()
                    mainViewModel.calculateSpecificHumidity()
                    mainViewModel.calculateSpecificHumidityAtSP()
                    mainViewModel.calculateDegreeOfSaturation()
                    mainViewModel.calculateDensityOfDryAir()
                    mainViewModel.calculateDensityOfWaterVapour()
                    mainViewModel.calculateDensityOfMoistAir()
                    mainViewModel.calculateSpecificVolumeOfUnSaturatedAir()
                    mainViewModel.calculateSpecificVolumeOfSaturatedAir()
                    mainViewModel.calculateEnthalpyOfVapour()
                    mainViewModel.calculateEnthalpyOfLiquid()
                    mainViewModel.calculateEnthalpyOfEvapouration()
                    mainViewModel.calculateEnthalpyOfDryAir()
                    mainViewModel.calculateEnthalpyOfWaterVapour()
                    mainViewModel.calculateEnthalpyOfMixture()






                    navController.navigate(BottomNavigationItems.screen2.route+"/$relativeHumidity/$dryBulbTemperature/$barometricPressure")
                          }, modifier = Modifier
                    .height(90.dp)
                    .weight(1f)
                    .padding(16.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    Color(
                        0xFF192BC2
                    )
                )
            ) {
                Text(text = "Calculate", fontSize = 20.sp)
            }
        }
    }

    LaunchedEffect(search.value, location) {
        location?.let {
            // Update local state variables when location changes
            mainViewModel.updateRelativeHumidity(it.current.humidity.toString())
            mainViewModel.updateDryBulbTemperature(it.current.temp_c.toString())
            mainViewModel.updateBarometricPressure((it.current.pressure_mb / 10).toString())
            search.value=false
        }
    }


}