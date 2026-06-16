package com.example.teachevent.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.sessionDataStore by preferencesDataStore(name = "session_prefs")

class SessionDataStore(private val context: Context) {
    companion object {
        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }

    val isLoggedIn: Flow<Boolean> = context.sessionDataStore.data
        .map { preferences -> preferences[IS_LOGGED_IN_KEY] == true }

    suspend fun saveSession(isLoggedIn: Boolean) {
        context.sessionDataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = isLoggedIn
        }
    }
}