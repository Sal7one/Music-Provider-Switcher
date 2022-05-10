package com.sal7one.musicswitcher.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sal7one.musicswitcher.utils.StringConstants
import javax.inject.Inject

class DataStoreProvider @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    object StoredKeys {
        val musicProvider = stringPreferencesKey(StringConstants.MUSIC_PREFERENCES_KEY.value)
        val playlistChoice = booleanPreferencesKey(StringConstants.PLAYLIST_PREFERENCES_KEY.value)
        val albumChoice = booleanPreferencesKey(StringConstants.ALBUM_PREFERENCES_KEY.value)
        val loadingChoice = booleanPreferencesKey(StringConstants.LOADING_PREFERENCES_KEY.value)

        // If Exception to ignore deep linking is needed
        val appleMusicException =
            booleanPreferencesKey(StringConstants.APPLE_M_PREFERENCES_KEY.value)
        val spotifyException = booleanPreferencesKey(StringConstants.SPOTIFY_PREFERENCES_KEY.value)
        val anghamiException = booleanPreferencesKey(StringConstants.ANGHAMI_PREFERENCES_KEY.value)
        val ytMusicException = booleanPreferencesKey(StringConstants.YT_MUSIC_PREFERENCES_KEY.value)
        val deezerException = booleanPreferencesKey(StringConstants.DEEZER_PREFERENCES_KEY.value)
    }

    suspend fun saveExceptions(
        appleMusic: Boolean,
        spotify: Boolean,
        anghami: Boolean,
        ytMusic: Boolean,
        deezer: Boolean,
        userLoadingChoice: Boolean
    ) {
        dataStore.edit { preference ->
            preference[StoredKeys.appleMusicException] = appleMusic
            preference[StoredKeys.spotifyException] = spotify
            preference[StoredKeys.anghamiException] = anghami
            preference[StoredKeys.ytMusicException] = ytMusic
            preference[StoredKeys.deezerException] = deezer
            preference[StoredKeys.loadingChoice] = userLoadingChoice
        }
    }

    suspend fun saveToDataStore(
        userMusicProvider: String,
        userPlaylist: Boolean,
        userAlbum: Boolean,
    ) {
        dataStore.edit { preference ->
            preference[StoredKeys.musicProvider] = userMusicProvider
            preference[StoredKeys.playlistChoice] = userPlaylist
            preference[StoredKeys.albumChoice] = userAlbum
        }
    }

    fun getFromDataStore() = dataStore.data
}