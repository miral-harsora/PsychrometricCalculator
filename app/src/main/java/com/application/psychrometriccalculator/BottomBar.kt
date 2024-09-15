package com.application.psychrometriccalculator

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.application.psychrometriccalculator.BottomNavigationItems.screen1.route
import com.application.psychrometriccalculator.BottomNavigationItems.screen1.selectedItem

@Composable
fun BottomBar(navHostController:NavHostController,modifier: Modifier) {
    val items = listOf(
        BottomNavigationItems.screen1,
        BottomNavigationItems.screen2,
        BottomNavigationItems.screen3
    )
    NavigationBar(
        containerColor = Color(0xFF192BC2),
    ) {
        val currentSelectedItem by rememberSaveable { mutableStateOf("") }
        var selectedItem by rememberSaveable { mutableStateOf(0) }
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                colors = androidx.compose.material3.NavigationBarItemDefaults
                    .colors(
                        selectedIconColor = Red,
                        indicatorColor = Color.Transparent
                    ),
                onClick = {
                    index.also { selectedItem =it }
                    if(item.route.equals("Results")) {
                        item.route = item.route.plus("/0.0/0.0/0.0")
                    }
                    Log.d("BottomBar", "BottomBar: ${item.route}")
                    navHostController.navigate(item.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (index == selectedItem) {
                                item.selectedItem
                            } else {
                                item.unSelectedItem
                            }
                        ),

                        tint = Color.White,
                        contentDescription = null
                    )

                },
                label = {
                    Text(
                        text =
                        if(item.route.contains("Results")){"Results"}else{item.route},
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            )
        }
    }
}