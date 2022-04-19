package com.sal7one.musicswitcher.compose

import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.sal7one.musicswitcher.compose.ui.theme.TextSecondary_color

@Composable
fun Switch(savedSwitchState: Boolean, onCheckChanged: (Boolean) -> Unit) {
    androidx.compose.material.Switch(
        checked = savedSwitchState,
        onCheckedChange = { onCheckChanged(it) },
        colors = SwitchDefaults.colors(
            checkedThumbColor = TextSecondary_color,
            uncheckedThumbColor = Color.White,
        )
    )
}