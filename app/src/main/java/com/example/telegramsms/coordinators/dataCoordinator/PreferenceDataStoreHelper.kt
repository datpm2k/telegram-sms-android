package com.example.telegramsms.coordinators.dataCoordinator

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore by preferencesDataStore(name = "PreferenceDataStore")

class ReferenceDataStoreHelper(context: Context) : IPreferenceDataStoreAPI {
    private val dataSource = context.dataStore

    override suspend fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataSource.data.catch { ex ->
            if (ex is IOException) {
                emit(emptyPreferences())
            } else {
                throw ex
            }
        }.map { preferences ->
            preferences[key] ?: defaultValue
        }
    }

    override suspend fun <T> getFirstPreference(key: Preferences.Key<T>, defaultValue: T): T {
        return dataSource.data.first()[key] ?: defaultValue
    }

    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        dataSource.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun <T> removePreference(key: Preferences.Key<T>) {
        dataSource.edit { preferences ->
            preferences.remove(key)
        }
    }

    override suspend fun clearAllPreference() {
        dataSource.edit { preferences ->
            preferences.clear()
        }
    }
}