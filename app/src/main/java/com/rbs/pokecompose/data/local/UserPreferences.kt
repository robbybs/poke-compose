package com.rbs.pokecompose.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {
    companion object {
        val IS_LOGIN_KEY = booleanPreferencesKey("is_login")
        val USERNAME_KEY = stringPreferencesKey("username")
    }

    val isLogin: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGIN_KEY] ?: false
    }

    val username: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USERNAME_KEY] ?: ""
    }

    suspend fun setLogin(isLogin: Boolean, username: String) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGIN_KEY] = isLogin
            if (isLogin) {
                preferences[USERNAME_KEY] = username
            } else {
                preferences.remove(USERNAME_KEY)
            }
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}