package com.sal7one.musicswitcher.controllers;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sal7one.musicswitcher.repository.DataStoreProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class OptionsViewModel(
    private val dataStoreManager: DataStoreProvider
) : ViewModel() {

    private val _OptionsViewModel = MutableStateFlow(TheScreenUiData())
    val OptionScreenState: StateFlow<TheScreenUiData> = _OptionsViewModel


    init {
        getData()
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

    fun changeValue(
        appleMusic: Boolean? = null,
        spotify: Boolean? = null,
        anghami: Boolean? = null,
        ytMusic: Boolean? = null,
        deezer: Boolean? = null,
    ) {
        _OptionsViewModel.update {
            when {
                appleMusic != null -> it.copy(appleMusic = !appleMusic)
                spotify != null -> it.copy(spotify = !spotify)
                anghami != null -> it.copy(anghami = !anghami)
                ytMusic != null -> it.copy(ytMusic = !ytMusic)
                deezer != null -> it.copy(deezer = !deezer)
                else -> it.copy()
            }
        }
    }

    private fun getData() = viewModelScope.launch(Dispatchers.IO) { // TODO Find Solution to this
        dataStoreManager.getFromDataStore().collect {
            val appleMusic = it[DataStoreProvider.StoredKeys.appleMusicException] ?: false
            val spotify = it[DataStoreProvider.StoredKeys.spotifyException] ?: false
            val anghami = it[DataStoreProvider.StoredKeys.anghamiException] ?: false
            val ytMusic = it[DataStoreProvider.StoredKeys.ytMusicException] ?: false
            val deezer = it[DataStoreProvider.StoredKeys.deezerException] ?: false

            _OptionsViewModel.value = TheScreenUiData(
                appleMusic = appleMusic,
                spotify = spotify,
                anghami = anghami,
                ytMusic = ytMusic,
                deezer = deezer
            )
        }
    }
}
