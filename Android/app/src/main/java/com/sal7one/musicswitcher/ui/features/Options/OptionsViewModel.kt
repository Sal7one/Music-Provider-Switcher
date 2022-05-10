package com.sal7one.musicswitcher.ui.features.Options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sal7one.musicswitcher.domain.model.ChooseMusicProviderUiData
import com.sal7one.musicswitcher.domain.repository.DataStoreProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OptionsViewModel @Inject constructor(
    private val dataStoreManager: DataStoreProvider
) : ViewModel() {

    private val _optionsViewModelStateFlow = MutableStateFlow(ChooseMusicProviderUiData())
    val optionScreenState: StateFlow<ChooseMusicProviderUiData> = _optionsViewModelStateFlow

    init {
        getData()
    }

    private fun saveExceptions(
        appleMusic: Boolean,
        spotify: Boolean,
        anghami: Boolean,
        ytMusic: Boolean,
        deezer: Boolean,
        loading: Boolean,
    ) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.saveExceptions(
            appleMusic = appleMusic,
            spotify = spotify,
            anghami = anghami,
            ytMusic = ytMusic,
            deezer = deezer,
            userLoadingChoice = loading
        )
    }

    fun changeValue(
        appleMusic: Boolean? = null,
        spotify: Boolean? = null,
        anghami: Boolean? = null,
        ytMusic: Boolean? = null,
        deezer: Boolean? = null,
        loading: Boolean? = null,
    ) {
        _optionsViewModelStateFlow.update {
            when {
                appleMusic != null -> it.copy(appleMusic = !appleMusic)
                spotify != null -> it.copy(spotify = !spotify)
                anghami != null -> it.copy(anghami = !anghami)
                ytMusic != null -> it.copy(ytMusic = !ytMusic)
                deezer != null -> it.copy(deezer = !deezer)
                loading != null -> it.copy(loading = !loading)
                else -> it.copy()
            }
        }
        // After changing ui state - Save values into Data store Directly
        saveExceptions(
            _optionsViewModelStateFlow.value.appleMusic,
            _optionsViewModelStateFlow.value.spotify,
            _optionsViewModelStateFlow.value.anghami,
            _optionsViewModelStateFlow.value.ytMusic,
            _optionsViewModelStateFlow.value.deezer,
            _optionsViewModelStateFlow.value.loading,
        )
    }

    private fun getData() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreManager.getFromDataStore().collect {
            val appleMusic = it[DataStoreProvider.StoredKeys.appleMusicException] ?: false
            val spotify = it[DataStoreProvider.StoredKeys.spotifyException] ?: false
            val anghami = it[DataStoreProvider.StoredKeys.anghamiException] ?: false
            val ytMusic = it[DataStoreProvider.StoredKeys.ytMusicException] ?: false
            val deezer = it[DataStoreProvider.StoredKeys.deezerException] ?: false
            val loading = it[DataStoreProvider.StoredKeys.loadingChoice] ?: true

            _optionsViewModelStateFlow.value = ChooseMusicProviderUiData(
                appleMusic = appleMusic,
                spotify = spotify,
                anghami = anghami,
                ytMusic = ytMusic,
                deezer = deezer,
                loading = loading,
            )
        }
    }
}