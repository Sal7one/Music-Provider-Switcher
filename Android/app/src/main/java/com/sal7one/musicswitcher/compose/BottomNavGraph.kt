package com.sal7one.musicswitcher.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(navController: NavHostController){
        NavHost(navController = navController, startDestination = "options" ){
        composable("home") { MainScreen() }
        composable("options") { OptionScreen() }
        composable("about") { AboutScreen() }
    }
}