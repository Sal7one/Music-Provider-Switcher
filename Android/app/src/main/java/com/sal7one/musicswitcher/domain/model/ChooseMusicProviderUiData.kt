package com.sal7one.musicswitcher.domain.model

data class ChooseMusicProviderUiData(
    val provider: String = "",
    val playListChoice: Boolean = false,
    val albumChoice: Boolean = false,
    val appleMusic: Boolean = false,
    val spotify: Boolean = false,
    val anghami: Boolean = false,
    val ytMusic: Boolean = false,
    val deezer: Boolean = false,
    val loading: Boolean = false
)