package com.sal7one.musicswitcher.repository.model

data class MusicProvider(
    val name: String,
    val searchLink: String,
    val packageReference: String,
    var overrulesPreference: Boolean = false,
    val icon : Int,
)