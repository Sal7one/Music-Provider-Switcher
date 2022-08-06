package com.sal7one.musicswitcher.ui.features.chooseMusicProvider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sal7one.musicswitcher.domain.model.ChooseMusicProviderUiData
import com.sal7one.musicswitcher.domain.repository.MusicPreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicProviderViewModel @Inject constructor(
    private val dataStoreManager: MusicPreferenceDataStore,
) : ViewModel() {

    private val _chooseMusicProviderScreenUiState = MutableStateFlow(ChooseMusicProviderUiData())
    val chooseMusicProviderScreenUiState: StateFlow<ChooseMusicProviderUiData> =
        _chooseMusicProviderScreenUiState

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
        _chooseMusicProviderScreenUiState.update {
            when {
                provider != null -> it.copy(provider = provider)
                playList != null -> it.copy(playListChoice = !playList)
                albums != null -> it.copy(albumChoice = !albums)
                else -> it.copy()
            }
        }
    }

    private fun getData() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.getFromDataStore().collect {
            val provider = it[MusicPreferenceDataStore.StoredKeys.musicProvider] ?: ""
            val playList = it[MusicPreferenceDataStore.StoredKeys.playlistChoice] ?: false
            val album = it[MusicPreferenceDataStore.StoredKeys.albumChoice] ?: false

            _chooseMusicProviderScreenUiState.value = ChooseMusicProviderUiData(
                provider = provider,
                playListChoice = playList,
                albumChoice = album,
            )
        }
    }
}