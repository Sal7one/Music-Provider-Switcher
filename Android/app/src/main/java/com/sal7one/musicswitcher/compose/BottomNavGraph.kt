package com.sal7one.musicswitcher.compose

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sal7one.musicswitcher.compose.screens.AboutScreen
import com.sal7one.musicswitcher.compose.screens.MainScreen
import com.sal7one.musicswitcher.compose.screens.OptionScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { MainScreen() }
        composable("options") { OptionScreen() }
        composable("about") { AboutScreen() }
    }
}