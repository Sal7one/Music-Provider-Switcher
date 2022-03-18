package com.sal7one.musicswitcher.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    val iconDescription: String
) {
    object MainScreen : Screen("home", Icons.Default.Home, "Home")
    object Options : Screen("options", Icons.Default.Edit, "Edit")
    object About : Screen("about", Icons.Default.Info, "About")
}