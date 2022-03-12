package com.sal7one.musicswitcher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sal7one.musicswitcher.repository.DataStoreProvider
import com.sal7one.musicswitcher.repository.model.TheScreenUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val dataStoreManager: DataStoreProvider
) : ViewModel() {

    private val _mainScreenUiState = MutableStateFlow(TheScreenUiData())
    val mainScreenUiState: StateFlow<TheScreenUiData> = _mainScreenUiState

    init {
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

    fun changeValue(
        provider: String? = null, playList: Boolean? = null, albums: Boolean? = null,
    ) {
        _mainScreenUiState.update {
            when {
                provider != null -> it.copy(provider = provider)
                playList != null -> it.copy(playList = !playList)
                albums != null -> it.copy(albums = !albums)
                else -> it.copy()
            }
        }
    }

    private fun getData() = viewModelScope.launch(Dispatchers.IO) { // TODO Find Solution to this
        dataStoreManager.getFromDataStore().collect {
            val provider = it[DataStoreProvider.StoredKeys.musicProvider] ?: ""
            val playList = it[DataStoreProvider.StoredKeys.playlistChoice] ?: false
            val album = it[DataStoreProvider.StoredKeys.albumChoice] ?: false

            _mainScreenUiState.value = TheScreenUiData(
                provider = provider,
                playList = playList,
                albums = album,
            )
        }
    }
}