package com.sal7one.musicswitcher.compose

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sal7one.musicswitcher.BottomNavGraph
import com.sal7one.musicswitcher.compose.ui.theme.*

@Composable
fun ApplicationScreen() {
    // Routes for bottom navigation
    val items = listOf(
        Screen.MainScreen,
        Screen.Options,
        Screen.About,
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        selectedContentColor = BottomNav_selected_color,
                        unselectedContentColor = BottomNav_unselected_color,
                        modifier = Modifier.background(BottomNav_background_color),
                        icon = { Icon(screen.icon, contentDescription = null) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        BottomNavGraph(navController = navController) // Nav host Graph
    }
}