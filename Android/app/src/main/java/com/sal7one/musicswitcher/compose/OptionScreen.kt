package com.sal7one.musicswitcher.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.material.Divider
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
import com.sal7one.musicswitcher.repository.model.MusicProvider
import com.sal7one.musicswitcher.repository.musicProviders

private var appleMusicChoice = false
private var spotifyChoice = false
private var anghamiChoice = false
private var ytMusicChoice = false
private var deezerChoice = false

@Composable
fun OptionScreen() {
    val context = LocalContext.current
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
            text = "Options and Exceptions",
            style = TextStyle(color = Color.White, fontSize = 24.sp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Here you can disable redirecting for a single provider (useful if a song is only available in one provider only)",
                modifier = Modifier
                    .padding(20.dp)
                    .scale(1.1f),
                style = MaterialTheme.typography.h2
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        OptionList(musicProviders)
        Spacer(modifier = Modifier.height(40.dp))
        UpdateButton()
    }
}

@Composable
fun OptionList(musicProviders: List<MusicProvider>) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            , modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier=Modifier.padding(start= 40.dp)) {
                Text(
                    text = "Music Provider",
                    style = TextStyle(fontSize = 20.sp)
                )                }
            Box(modifier=Modifier.padding(end= 20.dp)) {
                Text(
                    text = "Current Status",
                    style = TextStyle(fontSize = 20.sp)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(5.dp))
        musicProviders.forEach { musicProviderOption ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            , modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier=Modifier.padding(start= 51.dp)) {
                    Text(
                        text = musicProviderOption.name,
                        style = TextStyle(fontSize = 21.sp)
                    )                }
                Box(modifier=Modifier.padding(end= 65.dp)) {
                    Switch(musicProviderOption.overrulesPreference)
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Divider()
    }
}