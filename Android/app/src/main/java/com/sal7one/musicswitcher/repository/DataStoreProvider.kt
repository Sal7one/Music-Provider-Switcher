import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreProvider(val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "music_preferences")


    companion object {
        val music_provider = stringPreferencesKey("music_provider")

        @SuppressLint("StaticFieldLeak") // We're passing application context no memory leak is possible
        private var INSTANCE: DataStoreProvider? = null

        fun getInstance(context: Context): DataStoreProvider {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }
                val instance = DataStoreProvider(context)
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun savetoDataStore(WantedProvider: String) {
        context.dataStore.edit {
            it[music_provider] = WantedProvider

        }
    }

     fun getFromDataStore() = context.dataStore.data.map {
            it[music_provider] ?: ""
    }
}