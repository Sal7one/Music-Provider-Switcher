package com.sal7one.musicswitcher.controllers

import com.sal7one.musicswitcher.repository.DataStoreProvider
import android.net.Uri
import androidx.lifecycle.LiveData
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
    // Source data
    private val _chosenProvider: MutableLiveData<String> = MutableLiveData()
    private val _playlistChoice: MutableLiveData<Boolean> = MutableLiveData()
    private val _albumChoice: MutableLiveData<Boolean> = MutableLiveData()
    private val _appleMusicChoice: MutableLiveData<Boolean> = MutableLiveData()
    private val _spotifyChoice: MutableLiveData<Boolean> = MutableLiveData()
    private val _anghamiChoice: MutableLiveData<Boolean> = MutableLiveData()
    private val _ytMusicChoice: MutableLiveData<Boolean> = MutableLiveData()
    private val _deezerChoice: MutableLiveData<Boolean> = MutableLiveData()
    private val _musicPackage: MutableLiveData<String> = MutableLiveData()
    private val _searchLink: MutableLiveData<String> = MutableLiveData()
    private val _sameApp: MutableLiveData<Boolean> = MutableLiveData()
    private val _differentApp: MutableLiveData<Boolean> = MutableLiveData()
    private var isAlbum = true
    private var isPlaylist = true
    private var overrulesPreference = false

    // Exposed data
    val chosenProvider: LiveData<String> get() = _chosenProvider
    val playlistChoice: LiveData<Boolean> get() = _playlistChoice
    val albumChoice: LiveData<Boolean> get() = _albumChoice
    val appleMusicChoice: LiveData<Boolean> get() = _appleMusicChoice
    val spotifyChoice: LiveData<Boolean> get() = _spotifyChoice
    val anghamiChoice: LiveData<Boolean> get() = _anghamiChoice
    val ytMusicChoice: LiveData<Boolean> get() = _ytMusicChoice
    val deezerChoice: LiveData<Boolean> get() = _deezerChoice
    val musicPackage: LiveData<String> get() = _musicPackage
    val searchLink: LiveData<String> get() = _searchLink
    val sameApp: LiveData<Boolean> get() = _sameApp
    val differentApp: LiveData<Boolean> get() = _differentApp

    init {
        _appleMusicChoice.value = false
        _spotifyChoice.value = false
        _anghamiChoice.value = false
        _ytMusicChoice.value = false
        _deezerChoice.value = false
        _chosenProvider.value = ""
        _playlistChoice.value = false
        _albumChoice.value = false
        _sameApp.value = false
        _differentApp.value = false
        _musicPackage.value = ""
        _searchLink.value = ""
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

            _chosenProvider.postValue(provider)
            _playlistChoice.postValue(playList)
            _albumChoice.postValue(album)
            _appleMusicChoice.postValue(appleMusic)
            _spotifyChoice.postValue(spotify)
            _anghamiChoice.postValue(anghami)
            _ytMusicChoice.postValue(ytMusic)
            _deezerChoice.postValue(deezer)
            updatePackage(provider)
        }
    }

    fun handleDeepLink(data: Uri) = viewModelScope.launch(Dispatchers.IO) {
        val link = data.toString()
        if (link.contains(_chosenProvider.value.toString())) {
            _sameApp.postValue(true)
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
                _sameApp.postValue(true)
            } else {
                // Check if It should search in the chosen music provider and open it (sameAPP)
                if (isPlaylist && (_playlistChoice.value == false)) {
                    _sameApp.postValue(true)
                } else if (isAlbum && (_albumChoice.value == false)) {
                    _sameApp.postValue(true)
                } else {
                    _differentApp.postValue(true)
                }
            }
        }
    }

    private fun updatePackage(savedMusicProvider: String) {
        when {
            savedMusicProvider.contains(Constants.APPLE_MUSIC.link) -> {
                _musicPackage.postValue(Constants.APPLE_MUSIC_PACKAGE.link)
                _searchLink.postValue(Constants.APPLE_MUSIC_SEARCH.link)
            }
            savedMusicProvider.contains(Constants.SPOTIFY.link) -> {
                _musicPackage.postValue(Constants.SPOTIFY_PACKAGE.link)
                _searchLink.postValue(Constants.SPOTIFY_SEARCH.link)
            }
            savedMusicProvider.contains(Constants.ANGHAMI.link) -> {
                _musicPackage.postValue(Constants.ANGHAMI_PACKAGE.link)
                _searchLink.postValue(Constants.ANGHAMI_SEARCH.link)
            }
            savedMusicProvider.contains(Constants.YT_MUSIC.link) -> {
                _musicPackage.postValue(Constants.YT_MUSIC_PACKAGE.link)
                _searchLink.postValue(Constants.YT_MUSIC_SEARCH.link)
            }
            savedMusicProvider.contains(Constants.DEEZER.link) -> {
                _musicPackage.postValue(Constants.DEEZER_PACKAGE.link)
                _searchLink.postValue(Constants.DEEZER_SEARCH.link)
            }
        }
    }

    private fun updateMusicExceptions(musicProvider: String) {
        when {
            musicProvider.contains(Constants.APPLE_MUSIC.link) -> {
                overrulesPreference = _appleMusicChoice.value!!
            }
            musicProvider.contains(Constants.SPOTIFY.link) -> {
                overrulesPreference = _spotifyChoice.value!!
            }
            musicProvider.contains(Constants.ANGHAMI.link) -> {
                overrulesPreference = _anghamiChoice.value!!
            }
            musicProvider.contains(Constants.YT_MUSIC.link) -> {
                overrulesPreference = _ytMusicChoice.value!!
            }
            musicProvider.contains(Constants.DEEZER.link) -> {
                overrulesPreference = _deezerChoice.value!!
            }
        }
    }
}