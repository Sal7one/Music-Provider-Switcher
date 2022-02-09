package com.sal7one.musicswitcher.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sal7one.musicswitcher.utils.Constants

class DataStoreProvider(private val context: Context) {


    object StoredKeys {
        val musicProvider = stringPreferencesKey(Constants.MUSIC_PREFERENCES_KEY.link)
        val playlistChoice = booleanPreferencesKey(Constants.PLAYLIST_PREFERENCES_KEY.link)
        val albumChoice = booleanPreferencesKey(Constants.ALBUM_PREFERENCES_KEY.link)
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.MUSIC_PREFERENCES_DATASTORE.link)
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
        context.dataStore.edit { preference ->
            preference[StoredKeys.musicProvider] = userMusicProvider
            preference[StoredKeys.playlistChoice] = userPlaylist
            preference[StoredKeys.albumChoice] = userAlbum
        }
    }

    fun getFromDataStore() = context.dataStore.data
}