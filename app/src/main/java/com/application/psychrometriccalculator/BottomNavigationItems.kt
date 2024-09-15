package com.application.psychrometriccalculator

sealed class BottomNavigationItems(
    var route: String,
    val selectedItem: Int,
    val unSelectedItem: Int
) {
   object screen1: BottomNavigationItems(
    route = "Input",
    selectedItem = R.drawable.ic_key_filled,
    unSelectedItem = R.drawable.ic_key,
    )
    object screen2: BottomNavigationItems(
    route = "Results",
    selectedItem = R.drawable.ic_action_results_filled,
    unSelectedItem = R.drawable.ic_action_results
    )
    object screen3: BottomNavigationItems(
    route = "Help",
    selectedItem = R.drawable.ic_help_filled,
    unSelectedItem = R.drawable.ic_help

    )
}