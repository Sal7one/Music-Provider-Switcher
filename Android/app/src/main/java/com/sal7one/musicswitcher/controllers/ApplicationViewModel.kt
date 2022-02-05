package com.sal7one.musicswitcher.controllers

import com.sal7one.musicswitcher.repository.DataStoreProvider
import android.net.Uri
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sal7one.musicswitcher.utils.Constants

class ApplicationViewModel(

    private val dataStoreManager: DataStoreProvider
) : ViewModel() {

    var chosenProvider = MutableLiveData<String>()
    var musicPackage = MutableLiveData<String>()
    var searchLink = MutableLiveData<String>()
    var sameApp = MutableLiveData<Boolean>()
    var differentApp = MutableLiveData<Boolean>()

    init {
        chosenProvider.value = ""
        sameApp.value = false
        differentApp.value = false
        musicPackage.value = ""
        searchLink.value = ""
        getData()
    }

    fun saveData(userChoice: String) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.saveToDataStore(userChoice)
    }

    private fun getData() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.getFromDataStore().collect {
            chosenProvider.postValue(it)
            updatePackage(it)
        }
    }

    fun handleDeepLink(data: Uri) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.getFromDataStore().collect {
            handleMusicProvider(it, data)
        }
    }


    private fun handleMusicProvider(MusicProvider: String, data: Uri) {
        val link = data.toString()
        if (link.contains(MusicProvider)) {
            sameApp.postValue(true)
        } else {
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