package com.sal7one.musicswitcher.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationScreen(
    val route: String,
    val icon: ImageVector,
    val iconDescription: String
) {
    object MainScreen : NavigationScreen("home", Icons.Default.Home, "Home")
    object Options : NavigationScreen("options", Icons.Default.Edit, "Edit")
    object About : NavigationScreen("about", Icons.Default.Info, "About")
}