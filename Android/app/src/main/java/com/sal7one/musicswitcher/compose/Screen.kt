package com.sal7one.musicswitcher.compose

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.sal7one.musicswitcher.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
    val iconDescription: String
) {
    object MainScreen : Screen("home", R.string.nav_main,  Icons.Default.Home, "Home" )
    object Options : Screen("options", R.string.nav_options, Icons.Default.Edit, "Edit")
    object About : Screen("about", R.string.nav_about, Icons.Default.Info, "About")
}