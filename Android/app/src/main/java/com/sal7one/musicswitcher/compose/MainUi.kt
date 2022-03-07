package com.sal7one.musicswitcher.compose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.compose.ui.theme.*

class MainUi : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicSwitcherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                ) {
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
                        val textColor = TextSecondary_color

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
                                        textColor,
                                        CircleShape
                                    )
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onPress = { openGithub() },
                                        )
                                    }   // add a border (optional)
                            )
                            Text(
                                text = "Enjoy Music ;)",
                                modifier = Modifier.padding(start = 15.dp, top = 25.dp),
                                style = TextStyle(color = Color.White, fontSize = 24.sp),
                            )
                        }
                        Spacer(modifier = Modifier.height(80.dp))
                        Text(
                            text = getString(R.string.your_music_provider),
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
                        Spacer(modifier = Modifier.height(51.dp))
                        UpdateButton()

                    }
                }
            }
        }
    }

    private fun openGithub() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Sal7one")))
    }
}

@Composable
fun MusicProviderCard(musicIcon: Int) {
    val cardSize = dimensionResource(R.dimen.card_size)
    val cardColor = Card_color
    val shadowColor = CardGlow_color

    Box(
        modifier = Modifier
            .height(cardSize + 6.dp)
            .width(cardSize + 10.dp)
            .coloredShadow(
                shadowColor, alpha = 0.5f,
                shadowRadius = 5.dp,
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .height(cardSize)
                .width(cardSize + 4.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable { }
        ) {
            Box(
                modifier = Modifier
                    .background(color = cardColor)
                    .clip(shape = RoundedCornerShape(30.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(musicIcon), "content description",
                    modifier = Modifier
                        .fillMaxSize(0.8f),
                )
            }
        }
    }
}

@Composable
fun Switch(savedSwitchState: Boolean) {
    val checkedState = remember { mutableStateOf(savedSwitchState) }
    Switch(
        checked = checkedState.value,
        onCheckedChange = { checkedState.value = it },
        colors = SwitchDefaults.colors(
            checkedThumbColor = TextSecondary_color,
            uncheckedThumbColor = Color.White,
        )
    )
}

@Composable
fun UpdateButton() {
    val shadowColor = Color.White

    Box(
        modifier = Modifier
            .padding(start = 85.dp)
            .coloredShadow(
                shadowColor, alpha = 0.8f,
                shadowRadius = 11.dp,
            ),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = BtnUpdate_color,
            ),
            modifier = Modifier
                .clip(shape = RoundedCornerShape(7.dp))
                .width(178.dp),
            onClick = { /*TODO*/ })
        {
            Text(text = "Update Preferences", style = MaterialTheme.typography.button)
        }
    }
}