package com.sal7one.musicswitcher.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.domain.musicProviders
import com.sal7one.musicswitcher.ui.common.OptionList
import com.sal7one.musicswitcher.ui.features.Options.OptionsViewModel
import com.sal7one.musicswitcher.ui.ui.theme.AppPrimary_color
import com.sal7one.musicswitcher.ui.ui.theme.primary_gradient_color


@Composable
fun OptionScreen() {
    val context = LocalContext.current
    val viewModel: OptionsViewModel = viewModel()
    val theScreenUiState = viewModel.optionScreenState.collectAsState()

    val appleMusicChoice = theScreenUiState.value.appleMusic
    val spotifyChoice = theScreenUiState.value.spotify
    val anghamiChoice = theScreenUiState.value.anghami
    val ytMusicChoice = theScreenUiState.value.ytMusic
    val deezerChoice = theScreenUiState.value.deezer
    val loadingChoice = theScreenUiState.value.loading

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
            text = stringResource(R.string.optionsScreen_heading),
            style = TextStyle(color = Color.White, fontSize = 24.sp),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(R.string.optionScreen_explain),
                modifier = Modifier
                    .padding(20.dp)
                    .scale(1.1f),
                style = MaterialTheme.typography.h2
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(start = 40.dp)) {
                Text(
                    text = stringResource(R.string.musicprovider_text),
                    style = TextStyle(fontSize = 20.sp)
                )
            }
            Box(modifier = Modifier.padding(end = 20.dp)) {
                Text(
                    text = stringResource(R.string.exception_status),
                    style = TextStyle(fontSize = 20.sp)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        OptionList(musicProviders[0].name, appleMusicChoice) {
            viewModel.changeValue(appleMusic = appleMusicChoice)
            Toast.makeText(
                context,
                context.getString(R.string.updated_excpetions),
                Toast.LENGTH_LONG
            ).show()
        }
        OptionList(musicProviders[1].name, spotifyChoice) {
            viewModel.changeValue(spotify = spotifyChoice)
            Toast.makeText(
                context,
                context.getString(R.string.updated_excpetions),
                Toast.LENGTH_LONG
            ).show()
        }
        OptionList(musicProviders[2].name, anghamiChoice) {
            viewModel.changeValue(anghami = anghamiChoice)
            Toast.makeText(
                context,
                context.getString(R.string.updated_excpetions),
                Toast.LENGTH_LONG
            ).show()
        }
        OptionList(musicProviders[3].name, ytMusicChoice) {
            viewModel.changeValue(ytMusic = ytMusicChoice)
            Toast.makeText(
                context,
                context.getString(R.string.updated_excpetions),
                Toast.LENGTH_LONG
            ).show()
        }
        OptionList(musicProviders[4].name, deezerChoice) {
            viewModel.changeValue(deezer = deezerChoice)
            Toast.makeText(
                context,
                context.getString(R.string.updated_excpetions),
                Toast.LENGTH_LONG
            ).show()
        }
        Spacer(modifier = Modifier.height(5.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(start = 40.dp)) {
                Text(
                    text = stringResource(R.string.options),
                    style = TextStyle(fontSize = 20.sp)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        OptionList(stringResource(R.string.loading_after_clicking_link), loadingChoice) {
            viewModel.changeValue(loading = loadingChoice)
            Toast.makeText(
                context,
                context.getString(R.string.updated_options),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
