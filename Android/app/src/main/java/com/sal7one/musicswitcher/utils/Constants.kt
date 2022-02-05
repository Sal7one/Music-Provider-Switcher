package com.sal7one.musicswitcher.utils

enum class Constants(val link: String) {
    SPOTIFY_SEARCH("https://open.spotify.com/search/"),
    APPLE_MUSIC_SEARCH("http://music.apple.com/us/search?term="),
    ANGHAMI_SEARCH("https://play.anghami.com/search/"),
    DEEZER_SEARCH("deezer://www.deezer.com/search/"),
    YT_MUSIC_SEARCH("https://music.youtube.com/search?q="),
    SPOTIFY_PACKAGE("com.spotify.music"),
    APPLE_MUSIC_PACKAGE("com.apple.android.music"),
    ANGHAMI_PACKAGE("com.anghami"),
    DEEZER_PACKAGE("deezer.android.app"),
    YT_MUSIC_PACKAGE("com.google.android.apps.youtube.music"),
    SPOTIFY("open.spotify.com"),
    APPLE_MUSIC( "music.apple.com"),
    ANGHAMI("play.anghami.com"),
    DEEZER("deezer.com"),
    YT_MUSIC("music.youtube.com"),
    MUSIC_PREFERENCES_KEY("music_provider"),
    MUSIC_PREFERENCES_DATASTORE("music_preferences")
}
