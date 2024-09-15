package com.application.psychrometriccalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.application.psychrometriccalculator.repository.WeatherRepository
import com.application.psychrometriccalculator.ui.theme.PsychrometricCalculatorTheme

data class BottomNavigationItem(
    val title: String,
    val selectedItem: Int,
    val unSelectedItem: Int,

    )

class MainActivity : ComponentActivity() {
    lateinit var weatherRepository: WeatherRepository
    lateinit var mainViewModel: MainViewModel
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherRepository = (application as PsychometricApp).weatherRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(weatherRepository)).get(MainViewModel::class.java)

        setContent {
            PsychrometricCalculatorTheme {
                val navController: NavHostController = rememberNavController()

                Scaffold(
                    bottomBar = {

                        BottomBar(navHostController = navController, modifier = Modifier)
                    },


                    ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        NavigationGraph(navController = navController,mainViewModel)
                        Log.d("NavigationGraph", "NavigationGraph called ${BottomNavigationItems.screen1.route}")

                            navController.navigate(BottomNavigationItems.screen1.route)

                    }
                }
            }
        }


    }
}

/*
@Composable
fun BottomNavigation(mainActivity: MainActivity) {

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }


    bottomNavigation(mainActivity, navController, items = items,
        selectedItemIndex,
        updateSelectedItem = { selectedItemIndex = it }
        )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun bottomNavigation(
    context: MainActivity = MainActivity(),
    navController: NavHostController,
    items: List<BottomNavigationItems>,
    selectedItemIndex: Int,
    updateSelectedItem: (Int) -> Unit
) {
    navController.navigate(BottomNavigationItems.screen1.route)


}
*/





