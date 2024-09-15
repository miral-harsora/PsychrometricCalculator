package com.application.psychrometriccalculator

sealed class Routes (val route: String){
    object Input: Routes("input")
    object Results: Routes("results")
    object Help: Routes("help")
}