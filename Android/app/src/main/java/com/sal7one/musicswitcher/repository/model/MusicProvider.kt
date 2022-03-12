package com.sal7one.musicswitcher.repository.model

import androidx.compose.runtime.MutableState

data class MusicProvider(
    val name: String,
    val nameReference : String,
    val searchLink: String,
    val packageReference: String,
    var overrulesPreference: Boolean,
    val icon : Int,
)