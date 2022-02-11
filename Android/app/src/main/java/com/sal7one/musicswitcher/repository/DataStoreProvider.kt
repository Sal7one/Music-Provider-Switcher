package com.sal7one.musicswitcher.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sal7one.musicswitcher.utils.Constants
import kotlinx.coroutines.flow.map

class DataStoreProvider(private val context: Context) {




    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.MUSIC_PREFERENCES_DATASTORE.link)

        // Data types and (place) in datastore we're making.
        private val musicProvider = stringPreferencesKey(Constants.MUSIC_PREFERENCES_KEY.link)
        private val playlistChoice = booleanPreferencesKey(Constants.PLAYLIST_PREFERENCES_KEY.link)
        private val albumChoice = booleanPreferencesKey(Constants.ALBUM_PREFERENCES_KEY.link)
    }

    // We're passing application context no memory leak is possible
    private var instance: DataStoreProvider? = null

    fun getInstance(): DataStoreProvider {
        return instance ?: synchronized(this) {
            instance?.let {
                return it
            }
            val instance = DataStoreProvider(context)
            this.instance = instance
            instance
        }
    }

    suspend fun saveToDataStore(
        userMusicProvider: String,
        userPlaylist: Boolean,
        userAlbum: Boolean
    ) {
        context.dataStore.edit {
            it[musicProvider] = userMusicProvider
            it[playlistChoice] = userPlaylist
            it[albumChoice] = userAlbum
        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        val provider = it[musicProvider] ?: ""
        val playtlist = it[playlistChoice] ?: false
        val albumChoice = it[playlistChoice] ?: false
    }
}