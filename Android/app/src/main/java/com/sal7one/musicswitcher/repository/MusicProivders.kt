package com.sal7one.musicswitcher.repository

import com.sal7one.musicswitcher.R
import com.sal7one.musicswitcher.repository.model.MusicProvider
import com.sal7one.musicswitcher.utils.StringConstants


val musicProviders = listOf(
    MusicProvider(
        "Apple Music",
        StringConstants.APPLE_MUSIC.link,
        StringConstants.APPLE_MUSIC_SEARCH.link,
        StringConstants.APPLE_MUSIC_PACKAGE.link,
        false,
        R.drawable.apple_music_white,
    ),
    MusicProvider(
        "Spotify",
        StringConstants.SPOTIFY.link,

        StringConstants.SPOTIFY_SEARCH.link,
        StringConstants.SPOTIFY_PACKAGE.link,
        false,
        R.drawable.spotify_white,
    ),
    MusicProvider(
        "Anghami",
        StringConstants.ANGHAMI.link,
        StringConstants.ANGHAMI_SEARCH.link,
        StringConstants.ANGHAMI_PACKAGE.link,
        false,
        R.drawable.anghami_white,
    ),
    MusicProvider(
        "Youtube Music",
        StringConstants.YT_MUSIC.link,
        StringConstants.YT_MUSIC_SEARCH.link,
        StringConstants.YT_MUSIC_PACKAGE.link,
        false,
        R.drawable.yt_music_white,
    ),
    MusicProvider(
        "Deezer", StringConstants.DEEZER.link,
        StringConstants.DEEZER_SEARCH.link,
        StringConstants.DEEZER_PACKAGE.link,
        false,
        R.drawable.deezer_white,
    )
)