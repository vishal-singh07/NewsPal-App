package com.example.newspal.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val COUNTRY_PREFERENCES = "country_preference"

// Create a DataStore instance using the preferencesDataStore delegate, with the Context as
// receiver.
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = COUNTRY_PREFERENCES
)

class SettingsDataStore(preference_datastore: DataStore<Preferences>) {
    private val SELECTED_COUNTRY = stringPreferencesKey("country")

    val preferenceFlow: Flow<String> = preference_datastore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            // On the first run of the app, we will use india country by default
            preferences[SELECTED_COUNTRY] ?: "in"
        }

    suspend fun saveCountryToPreferencesStore(countryCode: String, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_COUNTRY] = countryCode
        }

    }
}
