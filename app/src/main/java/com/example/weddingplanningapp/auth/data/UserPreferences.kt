package com.example.weddingplanningapp.auth.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton



private val Context.dataStore by preferencesDataStore("user_prefs")

@Singleton
class UserPreferences @Inject constructor(private val context: Context) {

    private val USERNAME_KEY = stringPreferencesKey("username")
    private val PASSWORD_KEY = stringPreferencesKey("password")
    private val LOGGED_IN_KEY = booleanPreferencesKey("logged_in")

    val username: Flow<String?> = context.dataStore.data.map { it[USERNAME_KEY] }
    val password: Flow<String?> = context.dataStore.data.map { it[PASSWORD_KEY] }
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { it[LOGGED_IN_KEY] ?: false }

    suspend fun saveUser(username: String, password: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = username
            prefs[PASSWORD_KEY] = password
        }
    }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[LOGGED_IN_KEY] = loggedIn
        }
    }
}