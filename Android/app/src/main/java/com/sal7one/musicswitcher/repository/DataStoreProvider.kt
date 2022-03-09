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

        // If Exception to ignore deep linking is needed
        val appleMusicException = booleanPreferencesKey(Constants.APPLE_M_PREFERENCES_KEY.link)
        val spotifyException = booleanPreferencesKey(Constants.SPOTIFY_PREFERENCES_KEY.link)
        val anghamiException = booleanPreferencesKey(Constants.ANGHAMI_PREFERENCES_KEY.link)
        val ytMusicException = booleanPreferencesKey(Constants.YT_MUSIC_PREFERENCES_KEY.link)
        val deezerException = booleanPreferencesKey(Constants.DEEZER_PREFERENCES_KEY.link)
    }


    companion object {
        // One instance of this to avoid leaks
        private val Context.dataStore: DataStore<Preferences> by
        preferencesDataStore(name = Constants.MUSIC_PREFERENCES_DATASTORE.link)
    }

    // We're passing application context
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

    suspend fun saveExceptions(
        appleMusic: Boolean,
        spotify: Boolean,
        anghami: Boolean,
        ytMusic: Boolean,
        deezer: Boolean
    ) {
        context.dataStore.edit { preference ->
            preference[StoredKeys.appleMusicException] = appleMusic
            preference[StoredKeys.spotifyException] = spotify
            preference[StoredKeys.anghamiException] = anghami
            preference[StoredKeys.ytMusicException] = ytMusic
            preference[StoredKeys.deezerException] = deezer
        }
    }

    suspend fun saveToDataStore(
        userMusicProvider: String,
        userPlaylist: Boolean,
        userAlbum: Boolean,
    ) {
        context.dataStore.edit { preference ->
            preference[StoredKeys.musicProvider] = userMusicProvider
            preference[StoredKeys.playlistChoice] = userPlaylist
            preference[StoredKeys.albumChoice] = userAlbum
        }
    }

    fun getFromDataStore() = context.dataStore.data
}