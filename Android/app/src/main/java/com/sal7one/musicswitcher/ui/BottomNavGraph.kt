package com.sal7one.musicswitcher.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sal7one.musicswitcher.ui.screens.AboutScreen
import com.sal7one.musicswitcher.ui.screens.ChooseMusicProviderScreen
import com.sal7one.musicswitcher.ui.screens.OptionScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { ChooseMusicProviderScreen() }
        composable("options") { OptionScreen() }
        composable("about") { AboutScreen() }
    }
}