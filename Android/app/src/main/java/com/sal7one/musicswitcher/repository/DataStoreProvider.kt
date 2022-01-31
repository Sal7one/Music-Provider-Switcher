import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DateStoreProvider(val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "music_preferences")

    companion object {

        val music_provider = stringPreferencesKey("music_provider")
    }


    suspend fun savetoDataStore(WantedProvider: String) {
        context.dataStore.edit {

            it[music_provider] = WantedProvider

        }
    }

    suspend fun getFromDataStore() = context.dataStore.data.map {
            it[music_provider] ?: ""
    }
}