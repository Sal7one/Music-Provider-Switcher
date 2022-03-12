package com.sal7one.musicswitcher.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.compose.ui.theme.*
import com.sal7one.musicswitcher.repository.model.MusicProvider
import com.sal7one.musicswitcher.utils.Constants

@Composable
fun MusicProviderCard(
    MusicProvider: MusicProvider,
    chosenProvider: String,
    changeProvider: (String) -> Unit
) {
    val cardSize = dimensionResource(R.dimen.card_size)
    val shadowColor = CardGlow_color
    var cardColor by remember { mutableStateOf(Card_color) }

    Log.d("test0000","MusicProviderCard = $chosenProvider")

    if (MusicProvider.nameReference == chosenProvider) {
        when (chosenProvider) {
            Constants.APPLE_MUSIC.link -> cardColor = apple_clicked_color
            Constants.SPOTIFY.link -> cardColor = spotify_clicked_color
            Constants.ANGHAMI.link -> cardColor = anghami_clicked_color
            Constants.YT_MUSIC.link -> cardColor = ytMusic_clicked_color
            Constants.DEEZER.link -> cardColor = deezer_clicked_color
        }
    } else {
        cardColor = Card_color
    }
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
                .clickable { changeProvider(MusicProvider.nameReference) }
        ) {
            Box(
                modifier = Modifier
                    .background(color = cardColor)
                    .clip(shape = RoundedCornerShape(30.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(MusicProvider.icon), "content description",
                    modifier = Modifier
                        .fillMaxSize(0.8f),
                )
            }
        }
    }
}