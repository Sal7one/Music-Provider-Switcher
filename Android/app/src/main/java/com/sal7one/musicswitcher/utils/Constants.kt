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
    APPLE_MUSIC("music.apple.com"),
    ANGHAMI("play.anghami.com"),
    DEEZER("deezer.com"),
    YT_MUSIC("music.youtube.com"),
    MUSIC_PREFERENCES_KEY("music_provider"),
    PLAYLIST_PREFERENCES_KEY("playlist_choice"),
    ALBUM_PREFERENCES_KEY("album_choice"),
    APPLE_M_PREFERENCES_KEY("apple_music_choice"),
    SPOTIFY_PREFERENCES_KEY("spotify_choice"),
    ANGHAMI_PREFERENCES_KEY("anghami_choice"),
    YT_MUSIC_PREFERENCES_KEY("yt_music_choice"),
    DEEZER_PREFERENCES_KEY("deezer_choice"),
    MUSIC_PREFERENCES_DATASTORE("music_preferences")
}
