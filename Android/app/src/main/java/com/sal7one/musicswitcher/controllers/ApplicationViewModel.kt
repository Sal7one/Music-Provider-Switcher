package com.sal7one.musicswitcher.controllers

import com.sal7one.musicswitcher.repository.DataStoreProvider
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sal7one.musicswitcher.utils.Constants
import com.sal7one.musicswitcher.utils.typeofLink

class ApplicationViewModel(

    private val dataStoreManager: DataStoreProvider
) : ViewModel() {
    var chosenProvider = MutableLiveData<String>()
    var playlistChoice = MutableLiveData<Boolean>()
    var albumChoice = MutableLiveData<Boolean>()
    var appleMusicChoice = MutableLiveData<Boolean>()
    var spotifyChoice = MutableLiveData<Boolean>()
    var anghamiChoice = MutableLiveData<Boolean>()
    var ytMusicChoice = MutableLiveData<Boolean>()
    var deezerChoice = MutableLiveData<Boolean>()
    var musicPackage = MutableLiveData<String>()
    var searchLink = MutableLiveData<String>()
    var sameApp = MutableLiveData<Boolean>()
    var differentApp = MutableLiveData<Boolean>()
    private var isAlbum = true
    private var isPlaylist = true
    private var overrulesPreference = false

    init {
        appleMusicChoice.value = false
        spotifyChoice.value = false
        anghamiChoice.value = false
        ytMusicChoice.value = false
        deezerChoice.value = false

        chosenProvider.value = ""
        playlistChoice.value = false
        albumChoice.value = false
        sameApp.value = false
        differentApp.value = false
        musicPackage.value = ""
        searchLink.value = ""
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

            chosenProvider.postValue(provider)
            playlistChoice.postValue(playList)
            albumChoice.postValue(album)
            appleMusicChoice.postValue(appleMusic)
            spotifyChoice.postValue(spotify)
            anghamiChoice.postValue(anghami)
            ytMusicChoice.postValue(ytMusic)
            deezerChoice.postValue(deezer)
            updatePackage(provider)
        }
    }

    fun handleDeepLink(data: Uri) = viewModelScope.launch(Dispatchers.IO) {
        val link = data.toString()
        if (link.contains(chosenProvider.value.toString())) {
            sameApp.postValue(true)
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
                sameApp.postValue(true)
            } else {
                // Check if It should search in the chosen music provider and open it (sameAPP)
                if (isPlaylist && (playlistChoice.value == false)) {
                    sameApp.postValue(true)
                } else if (isAlbum && (albumChoice.value == false)) {
                    sameApp.postValue(true)
                } else {
                    differentApp.postValue(true)
                }
            }
        }
    }

    private fun updatePackage(savedMusicProvider: String) {
        when {
            savedMusicProvider.contains(Constants.APPLE_MUSIC.link) -> {
                musicPackage.postValue(Constants.APPLE_MUSIC_PACKAGE.link)
                searchLink.postValue(Constants.APPLE_MUSIC_SEARCH.link)
            }
            savedMusicProvider.contains(Constants.SPOTIFY.link) -> {
                musicPackage.postValue(Constants.SPOTIFY_PACKAGE.link)
                searchLink.postValue(Constants.SPOTIFY_SEARCH.link)
            }
            savedMusicProvider.contains(Constants.ANGHAMI.link) -> {
                musicPackage.postValue(Constants.ANGHAMI_PACKAGE.link)
                searchLink.postValue(Constants.ANGHAMI_SEARCH.link)
            }
            savedMusicProvider.contains(Constants.YT_MUSIC.link) -> {
                musicPackage.postValue(Constants.YT_MUSIC_PACKAGE.link)
                searchLink.postValue(Constants.YT_MUSIC_SEARCH.link)
            }
            savedMusicProvider.contains(Constants.DEEZER.link) -> {
                musicPackage.postValue(Constants.DEEZER_PACKAGE.link)
                searchLink.postValue(Constants.DEEZER_SEARCH.link)
            }
        }
    }

    private fun updateMusicExceptions(musicProvider: String) {
        when {
            musicProvider.contains(Constants.APPLE_MUSIC.link) -> {
                overrulesPreference = appleMusicChoice.value!!
            }
            musicProvider.contains(Constants.SPOTIFY.link) -> {
                overrulesPreference = spotifyChoice.value!!
            }
            musicProvider.contains(Constants.ANGHAMI.link) -> {
                overrulesPreference = anghamiChoice.value!!
            }
            musicProvider.contains(Constants.YT_MUSIC.link) -> {
                overrulesPreference = ytMusicChoice.value!!
            }
            musicProvider.contains(Constants.DEEZER.link) -> {
                overrulesPreference = deezerChoice.value!!
            }
        }
    }
}