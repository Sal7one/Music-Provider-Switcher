package com.sal7one.musicswitcher.compose

import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.sal7one.musicswitcher.compose.ui.theme.TextSecondary_color

@Composable
fun Switch(savedSwitchState: Boolean) {
    val checkedState = remember { mutableStateOf(savedSwitchState) }
    androidx.compose.material.Switch(
        checked = checkedState.value,
        onCheckedChange = { checkedState.value = it },
        colors = SwitchDefaults.colors(
            checkedThumbColor = TextSecondary_color,
            uncheckedThumbColor = Color.White,
        )
    )
}