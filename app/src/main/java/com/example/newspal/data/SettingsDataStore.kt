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

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = COUNTRY_PREFERENCES
)

class SettingsDataStore(context: Context) {
    private val appContext = context.applicationContext
    private val SELECTED_COUNTRY = stringPreferencesKey("country")

    val selectedCountryFlow: Flow<String> =appContext.dataStore.data
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

    suspend fun saveCountryToPreferencesStore(countryCode: String) {
        appContext.dataStore.edit { preferences ->
            preferences[SELECTED_COUNTRY] = countryCode
        }

    }
}
