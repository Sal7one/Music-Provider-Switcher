package com.sal7one.musicswitcher.compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.compose.ui.theme.*


@Composable
fun MainScreen() {
    val context = LocalContext.current
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
            )
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(R.mipmap.ic_launcher_clean_foreground),
                contentDescription = "avatar",
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
            Text(
                text = "Enjoy Music ;)",
                modifier = Modifier.padding(start = 15.dp, top = 25.dp),
                style = TextStyle(color = Color.White, fontSize = 24.sp),
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            MusicProviderCard(R.drawable.apple_music_white)
            Spacer(modifier = Modifier.width(10.dp))
            MusicProviderCard(R.drawable.spotify_white)
            Spacer(modifier = Modifier.width(10.dp))
            MusicProviderCard(R.drawable.yt_music_white)
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Spacer(modifier = Modifier.width(55.dp))
            MusicProviderCard(R.drawable.anghami_white)
            Spacer(modifier = Modifier.width(48.dp))
            MusicProviderCard(R.drawable.deezer_white)
        }
        Spacer(modifier = Modifier.height(25.dp))
        Box(modifier = Modifier.padding(start = 11.dp)) {
            Text(
                text = "Search for albums and playlists",
                style = MaterialTheme.typography.h2
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column() {
                Text(
                    "Album",
                    style = TextStyle(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.padding(5.dp)) {
                    Switch(false)
                }
            }
            Spacer(modifier = Modifier.width(48.dp))
            Column() {
                Text(
                    "Playlist",
                    style = TextStyle(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.padding(5.dp)) {
                    Switch(false)
                }
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Box(modifier = Modifier.padding( start = 15.dp)) {
            UpdateButton()
        }
    }
}