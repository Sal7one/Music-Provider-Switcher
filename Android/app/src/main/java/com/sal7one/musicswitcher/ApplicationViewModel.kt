package com.sal7one.musicswitcher

import DataStoreProvider
import android.net.Uri
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class ApplicationViewModel(

    private val dataStoreManager: DataStoreProvider
) : ViewModel() {

    var choosen_Provider  = MutableLiveData<String>()
    var Musicpackage  = MutableLiveData<String>()
    var searchLink  = MutableLiveData<String>()
    var sameApp  = MutableLiveData<Boolean>()
    var diffrentApp  = MutableLiveData<Boolean>()

    init {
        choosen_Provider.value = ""
        sameApp.value = false
        diffrentApp.value = false
        Musicpackage.value = ""
        searchLink.value = ""
        getData()
    }

    fun SaveData(userchoice : String) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.savetoDataStore(userchoice)
    }

    fun getData() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.getFromDataStore().collect {
            choosen_Provider.postValue(it)
            UpdatePackage(it)
        }
    }

    fun handleDeepLink(data: Uri) = GlobalScope.launch(Dispatchers.IO) {
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

    fun UpdatePackage(savedmusicProvider: String) {
        if(savedmusicProvider.contains( "open.spotify.com")){
            Musicpackage.postValue(Constants.SPOTIFY_PACKAGE.link)
            searchLink.postValue(Constants.SPOTIFY_SEARCH.link)
        }

        else if(savedmusicProvider.contains( "music.apple.com")){
            Musicpackage.postValue(Constants.APPLE_MUSIC_PACKAGE.link)
            searchLink.postValue(Constants.APPLE_MUSIC_SEARCH.link)
        }

        else if(savedmusicProvider.contains( "play.anghami.com")){
            Musicpackage.postValue(Constants.ANGHAMI_PACKAGE.link)
            searchLink.postValue(Constants.ANGHAMI_SEARCH_Link.link)
        }
        else if(savedmusicProvider.contains( "deezer")){
            Musicpackage.postValue(Constants.DEEZER_PACKAGE.link)
        }

    }
}