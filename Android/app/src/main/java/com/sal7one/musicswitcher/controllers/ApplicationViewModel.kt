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
    var musicPackage = MutableLiveData<String>()
    var searchLink = MutableLiveData<String>()
    var sameApp = MutableLiveData<Boolean>()
    var differentApp = MutableLiveData<Boolean>()
    private var isAlbum = true
    private var isPlaylist = true
    init {
        chosenProvider.value = ""
        sameApp.value = false
        differentApp.value = false
        playlistChoice.value = true
        albumChoice.value = true
        musicPackage.value = ""
        searchLink.value = ""
        getData()
    }

    fun saveData(
        userChoice: String,
        userPlaylist: Boolean,
        userAlbum: Boolean
    ) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.saveToDataStore(
            userMusicProvider = userChoice,
            userPlaylist = userPlaylist,
            userAlbum = userAlbum
        )
    }

    private fun getData() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.getFromDataStore().collect {
            val provider = it[DataStoreProvider.StoredKeys.musicProvider] ?: ""
            val playList = it[DataStoreProvider.StoredKeys.playlistChoice] ?: false
            val album = it[DataStoreProvider.StoredKeys.albumChoice] ?: false
            chosenProvider.postValue(provider)
            playlistChoice.postValue(playList)
            albumChoice.postValue(album)
            updatePackage(provider)
        }
    }

    fun handleDeepLink(data: Uri) = viewModelScope.launch(Dispatchers.IO) {
        val link = data.toString()

        if (link.contains(chosenProvider.value.toString())) {
            sameApp.postValue(true)
        } else {
            Log.d("MUSICMEE", "IN HERE")

            when(typeofLink(link)){
                "playlist" -> isPlaylist = true
                "album" -> isAlbum = true
            }

            Log.d("MUSICMEE", "Incoming: isPlaylist?: $isPlaylist || isAlbum?: $isAlbum")
            Log.d("MUSICMEE","|Playlist - Saved: ${
                when(playlistChoice.value){
                    false -> "yup"
                    true -> "nope"
                    else -> "--"
                }
            } <- is it off?")

            Log.d("MUSICMEE","|ALBUM - Saved: ${
                when(albumChoice.value){
                    false -> "yup"
                    true -> "nope"
                    else -> "--"
                }
            } <- is it off?")
            if(isPlaylist && playlistChoice.value == false)
                sameApp.postValue(true)
            else if(isAlbum && albumChoice.value == false)
                sameApp.postValue(true)
            else
            differentApp.postValue(true)
        }
    }

    private fun updatePackage(savedMusicProvider: String) {
        when {
            savedMusicProvider.contains(Constants.SPOTIFY.link) -> {
                musicPackage.postValue(Constants.SPOTIFY_PACKAGE.link)
                searchLink.postValue(Constants.SPOTIFY_SEARCH.link)
            }
            savedMusicProvider.contains(Constants.APPLE_MUSIC.link) -> {
                musicPackage.postValue(Constants.APPLE_MUSIC_PACKAGE.link)
                searchLink.postValue(Constants.APPLE_MUSIC_SEARCH.link)
            }
            savedMusicProvider.contains(Constants.ANGHAMI.link) -> {
                musicPackage.postValue(Constants.ANGHAMI_PACKAGE.link)
                searchLink.postValue(Constants.ANGHAMI_SEARCH.link)
            }
            savedMusicProvider.contains(Constants.DEEZER.link) -> {
                musicPackage.postValue(Constants.DEEZER_PACKAGE.link)
                searchLink.postValue(Constants.DEEZER_SEARCH.link)
            }
            savedMusicProvider.contains(Constants.YT_MUSIC.link) -> {
                musicPackage.postValue(Constants.YT_MUSIC_PACKAGE.link)
                searchLink.postValue(Constants.YT_MUSIC_SEARCH.link)
            }
        }
    }
}