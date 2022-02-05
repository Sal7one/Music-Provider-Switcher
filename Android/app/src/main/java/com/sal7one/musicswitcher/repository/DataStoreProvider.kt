package com.sal7one.musicswitcher.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sal7one.musicswitcher.utils.Constants
import kotlinx.coroutines.flow.map

class DataStoreProvider(private val context: Context) {

    private val musicProvider = stringPreferencesKey(Constants.MUSIC_PREFERENCES_KEY.link)

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

    suspend fun saveToDataStore(WantedProvider: String) {
        context.dataStore.edit {
            it[musicProvider] = WantedProvider

        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        it[musicProvider] ?: ""
    }
}