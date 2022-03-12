package com.sal7one.musicswitcher.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sal7one.musicswitcher.compose.ui.theme.BottomNav_background_color
import com.sal7one.musicswitcher.compose.ui.theme.BottomNav_selected_color
import com.sal7one.musicswitcher.compose.ui.theme.BottomNav_unselected_color

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
                        //label = {}, Text with screen Name If needed
                        icon = { Icon(screen.icon, contentDescription = screen.iconDescription) },
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
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BottomNavGraph(navController = navController) // Nav host Graph
        }
    }
}