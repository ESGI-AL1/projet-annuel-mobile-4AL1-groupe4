package com.example.acad.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.accessToken: DataStore<Preferences> by preferencesDataStore("access_token_pref")
val Context.refreshToken: DataStore<Preferences> by preferencesDataStore("refresh_token_pref")

class DataStoreRepository(context: Context) {
    private object PreferencesKey {
        val accessToken = stringPreferencesKey("access_token_state")
        val refreshToken = stringPreferencesKey("refresh_token_state")
    }

    private val accessToken = context.accessToken
    private val refreshToken = context.refreshToken

    suspend fun saveAccessToken(token: String) {
        accessToken.edit { preferences ->
            preferences[PreferencesKey.accessToken] = token
        }
    }

    suspend fun saveRefreshToken(token: String) {
        refreshToken.edit { preferences ->
            preferences[PreferencesKey.refreshToken] = token
        }
    }

    fun readAccessToken(): Flow<String> {
        return accessToken.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val accessToken = preferences[PreferencesKey.accessToken] ?: ""
            accessToken
        }
    }

    fun readRefreshToken(): Flow<String> {
        return refreshToken.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val refreshToken = preferences[PreferencesKey.refreshToken] ?: ""
            refreshToken
        }
    }

    suspend fun removeAccessToken() {
        accessToken.edit { preferences -> preferences.clear() }
    }

    suspend fun removeRefreshToken() {
        refreshToken.edit { preferences -> preferences.clear() }
    }
}