package com.sal7one.musicswitcher

import DataStoreProvider
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class MainActvityViewModel(

    private val dataStoreManager: DataStoreProvider
) : ViewModel() {

    var choosen_Provider  = MutableLiveData<String>()
    var Musicpackage  = MutableLiveData<String>()
    var sameApp  = MutableLiveData<Boolean>()

    init {
        choosen_Provider.value = ""
        sameApp.value = false
        Musicpackage.value = ""
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
        Log.d("MUSICMEE", MusicProvider.toString())
        Log.d("MUSICMEE", data.toString())
        if(link.contains(MusicProvider)){
            sameApp.postValue(true)
            Log.d("MUSICMEE", "Your provider is the same as the link")

        }else{
            Log.d("MUSICMEE", "diffrenet provider found")

            // open song in wanted app

        }

    }



    fun UpdatePackage(savedmusicProvider: String) {

        if(savedmusicProvider == "spotify")
            Musicpackage.postValue("com.spotify.music")
        else if(savedmusicProvider == "music.apple.com")
            Musicpackage.postValue("com.apple.android.music")
        else if(savedmusicProvider == "anghami")
            Musicpackage.postValue("com.anghami")
        else if(savedmusicProvider == "deezer")
            Musicpackage.postValue("deezer.android.app")


    }
}