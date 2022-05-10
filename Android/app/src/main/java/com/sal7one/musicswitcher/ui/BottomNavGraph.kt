package com.sal7one.musicswitcher.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sal7one.musicswitcher.ui.features.about.AboutScreen
import com.sal7one.musicswitcher.ui.features.chooseMusicProvider.ChooseMusicProviderScreen
import com.sal7one.musicswitcher.ui.features.options.OptionScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { ChooseMusicProviderScreen() }
        composable("options") { OptionScreen() }
        composable("about") { AboutScreen() }
    }
}