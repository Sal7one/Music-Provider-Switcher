package com.sal7one.musicswitcher.repository

import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.repository.model.MusicProvider
import com.sal7one.musicswitcher.utils.Constants


val musicProviders = listOf(
    MusicProvider(
        "Apple Music",
        Constants.APPLE_MUSIC_SEARCH.link,
        Constants.APPLE_MUSIC_PACKAGE.link,
        false,
        R.drawable.apple_music_white,
    ),
    MusicProvider(
        "Spotify",
        Constants.SPOTIFY_SEARCH.link,
        Constants.SPOTIFY_PACKAGE.link,
        false,
        R.drawable.spotify_white,
    ),
    MusicProvider(
        "Anghami",
        Constants.ANGHAMI_SEARCH.link,
        Constants.ANGHAMI_PACKAGE.link,
        false,
        R.drawable.anghami_white,
    ),
    MusicProvider(
        "Youtube Music",
        Constants.YT_MUSIC_SEARCH.link,
        Constants.YT_MUSIC_PACKAGE.link,
        false,
        R.drawable.yt_music_white,
    ),
    MusicProvider(
        "Deezer",
        Constants.DEEZER_SEARCH.link,
        Constants.DEEZER_PACKAGE.link,
        false,
        R.drawable.deezer_white,
    )
)