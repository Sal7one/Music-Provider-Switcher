package com.sal7one.musicswitcher.controllers

import com.sal7one.musicswitcher.repository.DataStoreProvider
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sal7one.musicswitcher.utils.Constants
import com.sal7one.musicswitcher.utils.typeofLink

class ApplicationViewModel(

    private val dataStoreManager: DataStoreProvider
) : ViewModel() {
    val chosenProvider: MutableState<String> = mutableStateOf("")
    val musicPackage: MutableState<String> = mutableStateOf("")
    val searchLink: MutableState<String> = mutableStateOf("")
    val playlistChoice: MutableState<Boolean> = mutableStateOf(false)
    val albumChoice: MutableState<Boolean> = mutableStateOf(false)
    val appleMusicChoice: MutableState<Boolean> = mutableStateOf(false)
    val spotifyChoice: MutableState<Boolean> = mutableStateOf(false)
    val anghamiChoice: MutableState<Boolean> = mutableStateOf(false)
    val ytMusicChoice: MutableState<Boolean> = mutableStateOf(false)
    val deezerChoice: MutableState<Boolean> = mutableStateOf(false)
    val sameApp: MutableState<Boolean> = mutableStateOf(false)
    val differentApp: MutableState<Boolean> = mutableStateOf(false)

    private var isAlbum = true
    private var isPlaylist = true
    private var overrulesPreference = false

    init {
        getData()
    }

    fun saveData(
        userChoice: String,
        userPlaylist: Boolean,
        userAlbum: Boolean,
    ) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.saveToDataStore(
            userMusicProvider = userChoice,
            userPlaylist = userPlaylist,
            userAlbum = userAlbum,
        )
    }

    fun saveExceptions(
        appleMusic: Boolean,
        spotify: Boolean,
        anghami: Boolean,
        ytMusic: Boolean,
        deezer: Boolean
    ) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.saveExceptions(
            appleMusic = appleMusic,
            spotify = spotify,
            anghami = anghami,
            ytMusic = ytMusic,
            deezer = deezer
        )
    }

    private fun getData() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.getFromDataStore().collect {
            val provider = it[DataStoreProvider.StoredKeys.musicProvider] ?: ""
            val playList = it[DataStoreProvider.StoredKeys.playlistChoice] ?: false
            val album = it[DataStoreProvider.StoredKeys.albumChoice] ?: false
            val appleMusic = it[DataStoreProvider.StoredKeys.appleMusicException] ?: false
            val spotify = it[DataStoreProvider.StoredKeys.spotifyException] ?: false
            val anghami = it[DataStoreProvider.StoredKeys.anghamiException] ?: false
            val ytMusic = it[DataStoreProvider.StoredKeys.ytMusicException] ?: false
            val deezer = it[DataStoreProvider.StoredKeys.deezerException] ?: false

            chosenProvider.value = (provider)
            playlistChoice.value = (playList)
            albumChoice.value = (album)
            appleMusicChoice.value = (appleMusic)
            spotifyChoice.value = (spotify)
            anghamiChoice.value = (anghami)
            ytMusicChoice.value = (ytMusic)
            deezerChoice.value = (deezer)
            updatePackage(provider)
        }
    }

    fun handleDeepLink(data: Uri) = viewModelScope.launch(Dispatchers.IO) {
        val link = data.toString()
        if (link.contains(chosenProvider.value)) {
            sameApp.value = (true)
        } else {
            // To ignore deep linking by request of user
            updateMusicExceptions(link)

            when (typeofLink(link)) {
                "playlist" -> {
                    isPlaylist = true
                    isAlbum = false
                }
                "album" -> {
                    isAlbum = true
                    isPlaylist = false
                }
                else -> {
                    isAlbum = false
                    isPlaylist = false
                }
            }

            // Ignores the chosen music provider
            // When the data is got from the datastore this gets updated in relation to the music provider
            if (overrulesPreference) {
                // Open same/original app
                sameApp.value = (true)
            } else {
                // Check if It should search in the chosen music provider and open it (sameAPP)
                if (isPlaylist && !playlistChoice.value) {
                    sameApp.value = (true)
                } else if (isAlbum && !albumChoice.value) {
                    sameApp.value = (true)
                } else {
                    differentApp.value = (true)
                }
            }
        }
    }

    private fun updatePackage(savedMusicProvider: String) {
        when {
            savedMusicProvider.contains(Constants.APPLE_MUSIC.link) -> {
                musicPackage.value = Constants.APPLE_MUSIC_PACKAGE.link
                searchLink.value = Constants.APPLE_MUSIC_SEARCH.link
            }
            savedMusicProvider.contains(Constants.SPOTIFY.link) -> {
                musicPackage.value = Constants.SPOTIFY_PACKAGE.link
                searchLink.value = Constants.SPOTIFY_SEARCH.link
            }
            savedMusicProvider.contains(Constants.ANGHAMI.link) -> {
                musicPackage.value = Constants.ANGHAMI_PACKAGE.link
                searchLink.value = Constants.ANGHAMI_SEARCH.link
            }
            savedMusicProvider.contains(Constants.YT_MUSIC.link) -> {
                musicPackage.value = Constants.YT_MUSIC_PACKAGE.link
                searchLink.value = Constants.YT_MUSIC_SEARCH.link
            }
            savedMusicProvider.contains(Constants.DEEZER.link) -> {
                musicPackage.value = Constants.DEEZER_PACKAGE.link
                searchLink.value = Constants.DEEZER_SEARCH.link
            }
        }
    }

    private fun updateMusicExceptions(musicProvider: String) {
        when {
            musicProvider.contains(Constants.APPLE_MUSIC.link) -> {
                overrulesPreference = appleMusicChoice.value
            }
            musicProvider.contains(Constants.SPOTIFY.link) -> {
                overrulesPreference = spotifyChoice.value
            }
            musicProvider.contains(Constants.ANGHAMI.link) -> {
                overrulesPreference = anghamiChoice.value
            }
            musicProvider.contains(Constants.YT_MUSIC.link) -> {
                overrulesPreference = ytMusicChoice.value
            }
            musicProvider.contains(Constants.DEEZER.link) -> {
                overrulesPreference = deezerChoice.value
            }
        }
    }
}