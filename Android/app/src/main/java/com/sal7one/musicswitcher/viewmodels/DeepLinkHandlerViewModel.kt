package com.sal7one.musicswitcher.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sal7one.musicswitcher.repository.DataStoreProvider
import com.sal7one.musicswitcher.utils.StringConstants
import com.sal7one.musicswitcher.utils.typeofLink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DeepLinkHandlerViewModel(
    private val dataStoreManager: DataStoreProvider
) : ViewModel() {
    private var _chosenProvider = MutableLiveData<String>()
    private var _playlistChoice = MutableLiveData<Boolean>()
    private var _albumChoice = MutableLiveData<Boolean>()
    private var _appleMusicChoice = MutableLiveData<Boolean>()
    private var _spotifyChoice = MutableLiveData<Boolean>()
    private var _anghamiChoice = MutableLiveData<Boolean>()
    private var _ytMusicChoice = MutableLiveData<Boolean>()
    private var _deezerChoice = MutableLiveData<Boolean>()
    private var _musicPackage = MutableLiveData<String>()
    private var _searchLink = MutableLiveData<String>()
    private var _sameApp = MutableLiveData<Boolean>()
    private var _differentApp = MutableLiveData<Boolean>()

    val chosenProvider: LiveData<String>
        get() = _chosenProvider
    private val playlistChoice: LiveData<Boolean>
        get() = _playlistChoice
    private val albumChoice: LiveData<Boolean>
        get() = _albumChoice
    private val appleMusicChoice: LiveData<Boolean>
        get() = _appleMusicChoice
    private val spotifyChoice: LiveData<Boolean>
        get() = _spotifyChoice
    private val anghamiChoice: LiveData<Boolean>
        get() = _anghamiChoice
    private val ytMusicChoice: LiveData<Boolean>
        get() = _ytMusicChoice
    private val deezerChoice: LiveData<Boolean>
        get() = _deezerChoice

    val musicPackage: LiveData<String>
        get() = _musicPackage
    val searchLink: LiveData<String>
        get() = _searchLink
    val sameApp: LiveData<Boolean>
        get() = _sameApp
    val differentApp: LiveData<Boolean>
        get() = _differentApp

    private var isAlbum = true
    private var isPlaylist = true
    private var overrulesPreference = false

    init {
        _sameApp.value = false
        _differentApp.value = false
        _musicPackage.value = ""
        _searchLink.value = ""
        getData()
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
        if (link.contains(chosenProvider.value.toString())) {
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
                if (isPlaylist && (playlistChoice.value == false)) {
                    _sameApp.postValue(true)
                } else if (isAlbum && (albumChoice.value == false)) {
                    _sameApp.postValue(true)
                } else {
                    _differentApp.postValue(true)
                }
            }
        }
    }

    private fun updatePackage(savedMusicProvider: String) {
        when {
            savedMusicProvider.contains(StringConstants.APPLE_MUSIC.link) -> {
                _musicPackage.postValue(StringConstants.APPLE_MUSIC_PACKAGE.link)
                _searchLink.postValue(StringConstants.APPLE_MUSIC_SEARCH.link)
            }
            savedMusicProvider.contains(StringConstants.SPOTIFY.link) -> {
                _musicPackage.postValue(StringConstants.SPOTIFY_PACKAGE.link)
                _searchLink.postValue(StringConstants.SPOTIFY_SEARCH.link)
            }
            savedMusicProvider.contains(StringConstants.ANGHAMI.link) -> {
                _musicPackage.postValue(StringConstants.ANGHAMI_PACKAGE.link)
                _searchLink.postValue(StringConstants.ANGHAMI_SEARCH.link)
            }
            savedMusicProvider.contains(StringConstants.YT_MUSIC.link) -> {
                _musicPackage.postValue(StringConstants.YT_MUSIC_PACKAGE.link)
                _searchLink.postValue(StringConstants.YT_MUSIC_SEARCH.link)
            }
            savedMusicProvider.contains(StringConstants.DEEZER.link) -> {
                _musicPackage.postValue(StringConstants.DEEZER_PACKAGE.link)
                _searchLink.postValue(StringConstants.DEEZER_SEARCH.link)
            }
        }
    }

    private fun updateMusicExceptions(musicProvider: String) {
        when {
            musicProvider.contains(StringConstants.APPLE_MUSIC.link) -> {
                overrulesPreference = appleMusicChoice.value!!
            }
            musicProvider.contains(StringConstants.SPOTIFY.link) -> {
                overrulesPreference = spotifyChoice.value!!
            }
            musicProvider.contains(StringConstants.ANGHAMI.link) -> {
                overrulesPreference = anghamiChoice.value!!
            }
            musicProvider.contains(StringConstants.YT_MUSIC.link) -> {
                overrulesPreference = ytMusicChoice.value!!
            }
            musicProvider.contains(StringConstants.DEEZER.link) -> {
                overrulesPreference = deezerChoice.value!!
            }
        }
    }
}