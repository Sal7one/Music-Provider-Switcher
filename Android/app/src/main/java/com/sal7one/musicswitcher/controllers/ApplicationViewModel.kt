package com.sal7one.musicswitcher.controllers

import DataStoreProvider
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

    var choosenProvider  = MutableLiveData<String>()
    var musicPackage  = MutableLiveData<String>()
    var searchLink  = MutableLiveData<String>()
    var sameApp  = MutableLiveData<Boolean>()
    var diffrentApp  = MutableLiveData<Boolean>()

    init {
        choosenProvider.value = ""
        sameApp.value = false
        diffrentApp.value = false
        musicPackage.value = ""
        searchLink.value = ""
        getData()
    }

    fun saveData(userchoice : String) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.savetoDataStore(userchoice)
    }

    private fun getData() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.getFromDataStore().collect {
            choosenProvider.postValue(it)
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
        if(link.contains(MusicProvider)){
            sameApp.postValue(true)
        }else{
            diffrentApp.postValue(true)
        }
    }

    private fun updatePackage(savedmusicProvider: String) {
        if(savedmusicProvider.contains( "open.spotify.com")){
            musicPackage.postValue(Constants.SPOTIFY_PACKAGE.link)
            searchLink.postValue(Constants.SPOTIFY_SEARCH.link)
        }

        else if(savedmusicProvider.contains( "music.apple.com")){
            musicPackage.postValue(Constants.APPLE_MUSIC_PACKAGE.link)
            searchLink.postValue(Constants.APPLE_MUSIC_SEARCH.link)
        }

        else if(savedmusicProvider.contains( "play.anghami.com")){
            musicPackage.postValue(Constants.ANGHAMI_PACKAGE.link)
            searchLink.postValue(Constants.ANGHAMI_SEARCH.link)
        }
        else if(savedmusicProvider.contains( "deezer.com")){
            musicPackage.postValue(Constants.DEEZER_PACKAGE.link)
            searchLink.postValue(Constants.DEEZER_SEARCH.link)
        }
        else if(savedmusicProvider.contains( "music.youtube.com")){
            musicPackage.postValue(Constants.YT_MUSIC_PACKAGE.link)
            searchLink.postValue(Constants.YT_MUSIC_SEARCH.link)
        }
    }
}