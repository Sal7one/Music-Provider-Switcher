package com.sal7one.musicswitcher.ui.features.deepLinkHandler

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.sal7one.musicswitcher.domain.model.ChooseMusicProviderUiData
import com.sal7one.musicswitcher.domain.model.DeepLinkUiData
import com.sal7one.musicswitcher.domain.model.ProviderExceptionsUiData
import com.sal7one.musicswitcher.domain.repository.DataStoreProvider
import com.sal7one.musicswitcher.utils.MusicHelpers
import com.sal7one.musicswitcher.utils.StringConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeepLinkHandlerViewModel @Inject constructor(
    private val dataStoreManager: DataStoreProvider,
    private val queue: RequestQueue,
) : ViewModel() {

    private val _deepLinkScreenState = MutableStateFlow(DeepLinkUiData())
    val deepLinkScreenState: StateFlow<DeepLinkUiData> = _deepLinkScreenState

    private var _songURI = MutableSharedFlow<String>()
    val songURI: SharedFlow<String> = _songURI.asSharedFlow()

    private val providerExceptionsState = MutableStateFlow(ProviderExceptionsUiData())
    private val musicProviderState = MutableStateFlow(ChooseMusicProviderUiData())

    var musicPackage = ""
    private var searchLink = ""
    private var isAlbum = true
    private var isPlaylist = true
    private var overrulesPreference = false

    init {
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
            val loading = it[DataStoreProvider.StoredKeys.loadingChoice] ?: false

            providerExceptionsState.value = ProviderExceptionsUiData(
                appleMusicChoice = appleMusic,
                spotifyChoice = spotify,
                anghamiChoice = anghami,
                ytMusicChoice = ytMusic,
                deezerChoice = deezer
            )

            musicProviderState.value = ChooseMusicProviderUiData(
                provider = provider,
                playListChoice = playList,
                albumChoice = album,
                loading = loading
            )
            updatePackage(provider)
        }
    }

    private fun requestSongData(songURL: String) = viewModelScope.launch(Dispatchers.IO) {
        val songRequest = StringRequest(
            Request.Method.GET, songURL,
            { response ->
                val songName = MusicHelpers.extractFromURL(songURL, response)
                val searchURL = searchLink
                val query: String = Uri.encode(songName, "utf-8")

                // Different Provider - differentApp
                val uri = Uri.parse(searchURL + query)

                viewModelScope.launch {
                    _songURI.emit(uri.toString())
                }
            }, {
                Log.e("DEEP_LINK", "Volley Error")
            }
        )
        queue.add(songRequest)
    }

    fun handleDeepLink(data: Uri) = viewModelScope.launch(Dispatchers.IO) {
        val link = data.toString()
        val sameAppAsLink = link.contains(musicProviderState.value.provider)

        if (sameAppAsLink) {
            setAppPackageSameAsLink(link)
            _songURI.emit(link)
        } else {
            // Checks if music provider has exception to overrule music provider
            updateMusicExceptions(link)

            // Checks link type (Playlist, Album)
            // to decide whether to search that in the chosen provider or in the app of the link
            updateSearchExceptions(link)

            // Open same/original app for this link without checking anything else
            if (overrulesPreference) {
                setAppPackageSameAsLink(link)
            } else {
                // Checks link type (Playlist, Album)
                if (isPlaylist && !musicProviderState.value.playListChoice ||
                    isAlbum && !musicProviderState.value.albumChoice
                ) {
                    setAppPackageSameAsLink(link)
                } else {
                    requestSongData(link)
                }
            }
        }
    }

    private fun setAppPackageSameAsLink(data: String) {
        musicPackage = MusicHelpers.getMusicAppPackage(data)
    }

    private fun updateSearchExceptions(link: String) {
        when (MusicHelpers.typeofLink(link)) {
            StringConstants.LINK_TYPE_PLAYLIST.value -> {
                isPlaylist = true
                isAlbum = false
            }
            StringConstants.LINK_TYPE_ALBUM.value -> {
                isAlbum = true
                isPlaylist = false
            }
            else -> {
                isAlbum = false
                isPlaylist = false
            }
        }
    }

    private fun updatePackage(savedMusicProvider: String) {
        when {
            savedMusicProvider.contains(StringConstants.APPLE_MUSIC.value) -> {
                musicPackage = StringConstants.APPLE_MUSIC_PACKAGE.value
                searchLink = StringConstants.APPLE_MUSIC_SEARCH.value
            }
            savedMusicProvider.contains(StringConstants.SPOTIFY.value) -> {
                musicPackage = StringConstants.SPOTIFY_PACKAGE.value
                searchLink = StringConstants.SPOTIFY_SEARCH.value
            }
            savedMusicProvider.contains(StringConstants.ANGHAMI.value) -> {
                musicPackage = StringConstants.ANGHAMI_PACKAGE.value
                searchLink = StringConstants.ANGHAMI_SEARCH.value
            }
            savedMusicProvider.contains(StringConstants.YT_MUSIC.value) -> {
                musicPackage = StringConstants.YT_MUSIC_PACKAGE.value
                searchLink = StringConstants.YT_MUSIC_SEARCH.value
            }
            savedMusicProvider.contains(StringConstants.DEEZER.value) -> {
                musicPackage = StringConstants.DEEZER_PACKAGE.value
                searchLink = StringConstants.DEEZER_SEARCH.value
            }
        }
    }

    private fun updateMusicExceptions(musicProvider: String) {
        when {
            musicProvider.contains(StringConstants.APPLE_MUSIC.value) -> {
                overrulesPreference = providerExceptionsState.value.appleMusicChoice
            }
            musicProvider.contains(StringConstants.SPOTIFY.value) -> {
                overrulesPreference = providerExceptionsState.value.spotifyChoice
            }
            musicProvider.contains(StringConstants.ANGHAMI.value) -> {
                overrulesPreference = providerExceptionsState.value.anghamiChoice
            }
            musicProvider.contains(StringConstants.YT_MUSIC.value) -> {
                overrulesPreference = providerExceptionsState.value.ytMusicChoice
            }
            musicProvider.contains(StringConstants.DEEZER.value) -> {
                overrulesPreference = providerExceptionsState.value.deezerChoice
            }
        }
    }
}