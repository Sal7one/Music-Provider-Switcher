package com.sal7one.musicswitcher

import DateStoreProvider
import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class MainActvityViewModel(application: Application) : AndroidViewModel(application) {

    val dataStoreManager = DateStoreProvider(this.getApplication())
    var choosen_Provider  = MutableLiveData<String>()

    init {
        choosen_Provider.value = ""
        getData()
    }

    fun SaveData(userchoice : String) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.savetoDataStore(userchoice)
    }

    fun getData() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.getFromDataStore().catch { e ->
            e.printStackTrace()
        }.collect {
            choosen_Provider.postValue(it)
        }
    }

    fun handleDeepLink(data: Uri) = GlobalScope.launch(Dispatchers.IO) {
        dataStoreManager.getFromDataStore().catch { e ->
            e.printStackTrace()

        }.collect {
            handleMusicProvider(it, data)
        }
    }



    private fun handleMusicProvider(MusicProvider: String, data: Uri) {
//        val link = data.toString()
//        MusicProvider.contains("open.spotify.com");
//         openSpotify()
//        MusicProvider.contains("music.apple.com");
//         openSpotify()
//        MusicProvider.contains("play.anghami.com");
//         openSpotify()
//        Log.d("Linker" , link)

        // open app

    }

    fun openSpotify(){

    }
}