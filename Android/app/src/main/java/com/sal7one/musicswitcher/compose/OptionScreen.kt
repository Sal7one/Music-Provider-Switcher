package com.sal7one.musicswitcher.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sal7one.musicswitcher.compose.ui.theme.AppPrimary_color
import com.sal7one.musicswitcher.compose.ui.theme.primary_gradient_color



@Composable
fun OptionScreen() {
    val context = LocalContext.current
    var appleMusicChoice = false
    var spotifyChoice = false
    var anghamiChoice = false
    var ytMusicChoice = false
    var deezerChoice = false
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppPrimary_color,
                        AppPrimary_color,
                        AppPrimary_color,
                        primary_gradient_color
                    )
                )
            )
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Options and Preferences",
            style = TextStyle(color = Color.White, fontSize = 24.sp),
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Here you can disable redirecting temporarily",
                modifier = Modifier
                    .padding(20.dp)
                    .scale(1.1f),
                style = MaterialTheme.typography.h2
            )
        }
    }
}

@Composable
fun OptionList(Options: List<MusicOption>) {
    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        Options.forEach { musicProviderOption ->
            Row() {
                Text(text = musicProviderOption.title)
                Switch(savedSwitchState = musicProviderOption.isChecked)
            }
        }
    }
}