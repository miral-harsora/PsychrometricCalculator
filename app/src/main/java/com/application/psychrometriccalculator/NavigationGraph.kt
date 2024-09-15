package com.application.psychrometriccalculator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun NavigationGraph(navController: NavHostController, mainViewModel: MainViewModel) {
    NavHost(navController, startDestination = Routes.Input.route) {
        composable(Routes.Input.route) {
            InputScreen(navController,mainViewModel)
        }
        composable(
            Routes.Results.route + "/{my_param}/{my_param2}/{my_param3}",
            arguments = listOf(
                navArgument("my_param") {
                    type = NavType.StringType
                },
                navArgument("my_param2") {
                    type = NavType.StringType
                },
                navArgument("my_param3") {
                    type = NavType.StringType
                },
            )
        ) {
            val param = it.arguments?.getString("my_param")
            val param2 = it.arguments?.getString("my_param2")
            val param3 = it.arguments?.getString("my_param3")
            ResultsScreen(param = param, param2 = param2, param3 = param3)
        }
        composable(Routes.Help.route) {
            HelpScreen()
        }

    }
}