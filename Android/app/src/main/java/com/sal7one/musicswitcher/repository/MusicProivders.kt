package com.sal7one.musicswitcher.repository

import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.repository.model.MusicProvider
import com.sal7one.musicswitcher.utils.StringConstants


val musicProviders = listOf(
    MusicProvider(
        "Apple Music",
        StringConstants.APPLE_MUSIC.value,
        StringConstants.APPLE_MUSIC_SEARCH.value,
        StringConstants.APPLE_MUSIC_PACKAGE.value,
        false,
        R.drawable.apple_music_white,
    ),
    MusicProvider(
        "Spotify",
        StringConstants.SPOTIFY.value,

        StringConstants.SPOTIFY_SEARCH.value,
        StringConstants.SPOTIFY_PACKAGE.value,
        false,
        R.drawable.spotify_white,
    ),
    MusicProvider(
        "Anghami",
        StringConstants.ANGHAMI.value,
        StringConstants.ANGHAMI_SEARCH.value,
        StringConstants.ANGHAMI_PACKAGE.value,
        false,
        R.drawable.anghami_white,
    ),
    MusicProvider(
        "Youtube Music",
        StringConstants.YT_MUSIC.value,
        StringConstants.YT_MUSIC_SEARCH.value,
        StringConstants.YT_MUSIC_PACKAGE.value,
        false,
        R.drawable.yt_music_white,
    ),
    MusicProvider(
        "Deezer", StringConstants.DEEZER.value,
        StringConstants.DEEZER_SEARCH.value,
        StringConstants.DEEZER_PACKAGE.value,
        false,
        R.drawable.deezer_white,
    )
)