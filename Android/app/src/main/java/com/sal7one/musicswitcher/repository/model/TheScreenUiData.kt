package com.sal7one.musicswitcher.repository.model

data class TheScreenUiData(
    val provider:String="",
    val playList:Boolean=false,
    val albums:Boolean=false,
    val appleMusic:Boolean=false,
    val spotify:Boolean=false,
    val anghami:Boolean=false,
    val ytMusic:Boolean=false,
    val deezer:Boolean=false
)