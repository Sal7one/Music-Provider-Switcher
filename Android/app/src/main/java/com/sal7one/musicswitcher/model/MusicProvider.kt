package com.sal7one.musicswitcher.model

data class MusicProvider(
    val name: String,
    val searchLink: String,
    val packageReference: String,
    val overrulesPreference: Boolean = false,
    val icon : Int,
)