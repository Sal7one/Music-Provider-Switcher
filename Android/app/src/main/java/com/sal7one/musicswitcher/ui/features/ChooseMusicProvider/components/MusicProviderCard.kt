package com.sal7one.musicswitcher.ui.common

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
import com.sal7one.musicswitcher.ui.ui.theme.*
import com.sal7one.musicswitcher.domain.model.MusicProvider
import com.sal7one.musicswitcher.utils.StringConstants

@Composable
fun MusicProviderCard(
    MusicProvider: MusicProvider,
    chosenProvider: String,
    changeProvider: (String) -> Unit
) {
    val cardSize = dimensionResource(R.dimen.card_size)
    var shadowColor by remember { mutableStateOf(CardGlow_color) }
    var cardColor by remember { mutableStateOf(Card_color) }


    if (MusicProvider.nameReference == chosenProvider) {
        when (chosenProvider) {
            StringConstants.APPLE_MUSIC.value -> {
                cardColor = apple_clicked_color
                shadowColor = apple_clicked_color
            }
            StringConstants.SPOTIFY.value -> {
                cardColor = spotify_clicked_color
                shadowColor = spotify_clicked_color
            }
            StringConstants.ANGHAMI.value -> {
                cardColor = anghami_clicked_color
                shadowColor = anghami_clicked_color
            }
            StringConstants.YT_MUSIC.value -> {
                cardColor = ytMusic_clicked_color
                shadowColor = ytMusic_clicked_color
            }
            StringConstants.DEEZER.value -> {
                cardColor = deezer_clicked_color
                shadowColor = deezer_clicked_color
            }
        }
    } else {
        cardColor = Card_color
        shadowColor = CardGlow_color
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