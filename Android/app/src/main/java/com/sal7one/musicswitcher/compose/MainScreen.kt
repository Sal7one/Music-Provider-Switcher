package com.sal7one.musicswitcher.compose

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.compose.ui.theme.*
import com.sal7one.musicswitcher.controllers.ApplicationViewModel
import com.sal7one.musicswitcher.controllers.MyViewModelFactory
import com.sal7one.musicswitcher.repository.DataStoreProvider
import com.sal7one.musicswitcher.repository.musicProviders
import com.sal7one.musicswitcher.utils.Constants


@Composable
fun MainScreen() {
    val context = LocalContext.current
    val showExplainDialog = remember { mutableStateOf(false) }
    val dataStoreProvider = DataStoreProvider(context.applicationContext).getInstance()
    val viewModel: ApplicationViewModel = viewModel(factory = MyViewModelFactory(dataStoreProvider))

    val albumChoice by viewModel.albumChoice.observeAsState();
    val playlistChoice by viewModel.playlistChoice.observeAsState();
    val currentProvider by viewModel.chosenProvider.observeAsState();


    Column(
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
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(R.mipmap.ic_launcher_clean_foreground),
                contentDescription = "app icon",
                contentScale = ContentScale.Crop,  // crop the image if it's not a square
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape) // clip to the circle shape
                    .border(
                        2.dp,
                        TextSecondary_color,
                        CircleShape
                    )
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://github.com/Sal7one")
                                    )
                                )
                            },
                        )
                    }   // add a border (optional)
            )
            if (showExplainDialog.value) {
                CustomDialog(openDialogCustom = showExplainDialog)
            }
            Text(
                text = stringResource(R.string.mainscreen_heading),
                modifier = Modifier.padding(start = 15.dp),
                style = TextStyle(color = Color.White, fontSize = 24.sp),
            )

            Image(
                painter = painterResource(R.drawable.infoicon),
                contentDescription = stringResource(R.string.main_screen_info_icon),
                modifier = Modifier
                    .scale(1.8f)
                    .padding(start = 30.dp, top = 2.dp)
                    .clickable { showExplainDialog.value = true }
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = context.getString(R.string.your_music_provider),
            modifier = Modifier.padding(start = 10.dp),
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {

            MusicProviderCard(musicProviders[0], currentProvider.toString())
            MusicProviderCard(musicProviders[1], currentProvider.toString())
            MusicProviderCard(musicProviders[2], currentProvider.toString())
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            MusicProviderCard(musicProviders[3], currentProvider.toString())
            Spacer(modifier = Modifier.width(50.dp))
            MusicProviderCard(musicProviders[4], currentProvider.toString())
        }
        Spacer(modifier = Modifier.height(25.dp))
        Box(modifier = Modifier.padding(start = 11.dp)) {
            Text(
                text = stringResource(R.string.mainscreen_album_playlist_text),
                style = MaterialTheme.typography.h2
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    stringResource(R.string.album),
                    style = TextStyle(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.padding(5.dp)) {
                    Switch(albumChoice!!)
                }
            }
            Spacer(modifier = Modifier.width(50.dp))
            Column {
                Text(
                    stringResource(R.string.playlist),
                    style = TextStyle(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.padding(5.dp)) {
                    Switch(playlistChoice!!)
                }
            }
        }
        Spacer(
            modifier = Modifier.height(
                25.dp
            )
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Box(
                Modifier.clickable {
                    if (currentProvider!!.isNotBlank()) {
                        viewModel.saveData(
                            currentProvider!!,
                            playlistChoice!!,
                            albumChoice!!,
                        )
                        Toast.makeText(context, "Preference Updated!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Chose an option to update",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            ) {
                UpdateButton(stringResource(R.string.mainscreen_update_pref_btn))
            }
        }
    }
}