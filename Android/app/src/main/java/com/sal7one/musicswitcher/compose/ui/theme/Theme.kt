package com.sal7one.musicswitcher.compose.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

@Composable
fun MusicSwitcherTheme(
    content: @Composable () -> Unit
) {
    val colors = darkColors()

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}