package com.sal7one.musicswitcher

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sal7one.musicswitcher.compose.AboutScreen
import com.sal7one.musicswitcher.compose.MainScreen
import com.sal7one.musicswitcher.compose.OptionScreen

@Composable
fun BottomNavGraph(navController: NavHostController){
    
        NavHost(navController = navController, startDestination = "home" ){
        composable("home") { MainScreen() }
        composable("options") { OptionScreen() }
        composable("about") { AboutScreen() }
    }
}