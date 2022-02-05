package com.sal7one.musicswitcher.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreProvider(private val context: Context) {

    private val musicProvider = stringPreferencesKey("music_provider")
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "music_preferences")
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

    suspend fun savetoDataStore(WantedProvider: String) {
        context.dataStore.edit {
            it[musicProvider] = WantedProvider

        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        it[musicProvider] ?: ""
    }
}