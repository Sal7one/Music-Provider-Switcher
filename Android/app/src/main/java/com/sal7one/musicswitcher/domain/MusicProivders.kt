package com.sal7one.musicswitcher.domain

import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.domain.model.MusicProvider
import com.sal7one.musicswitcher.utils.StringConstants


val musicProviders = listOf(
    MusicProvider(
        StringConstants.APPLE_MUSIC_NAME.value,
        StringConstants.APPLE_MUSIC.value,
        StringConstants.APPLE_MUSIC_SEARCH.value,
        StringConstants.APPLE_MUSIC_PACKAGE.value,
        false,
        R.drawable.apple_music_white,
    ),
    MusicProvider(
        StringConstants.SPOTIFY_NAME.value,
        StringConstants.SPOTIFY.value,
        StringConstants.SPOTIFY_SEARCH.value,
        StringConstants.SPOTIFY_PACKAGE.value,
        false,
        R.drawable.spotify_white,
    ),
    MusicProvider(
        StringConstants.ANGHAMI_NAME.value,
        StringConstants.ANGHAMI.value,
        StringConstants.ANGHAMI_SEARCH.value,
        StringConstants.ANGHAMI_PACKAGE.value,
        false,
        R.drawable.anghami_white,
    ),
    MusicProvider(
        StringConstants.YT_MUSIC_NAME.value,
        StringConstants.YT_MUSIC.value,
        StringConstants.YT_MUSIC_SEARCH.value,
        StringConstants.YT_MUSIC_PACKAGE.value,
        false,
        R.drawable.yt_music_white,
    ),
    MusicProvider(
        StringConstants.DEEZER_NAME.value, StringConstants.DEEZER.value,
        StringConstants.DEEZER_SEARCH.value,
        StringConstants.DEEZER_PACKAGE.value,
        false,
        R.drawable.deezer_white,
    )
)