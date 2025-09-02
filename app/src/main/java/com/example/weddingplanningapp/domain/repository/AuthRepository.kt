package com.example.weddingplanningapp.domain.repository

import com.example.weddingplanningapp.auth.data.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val preferences: UserPreferences) {

    suspend fun register(username: String, password: String) {
        preferences.saveUser(username, password)
    }

    suspend fun login(username: String, password: String): Boolean {
        val storedUsername = preferences.username.first()
        val storedPassword = preferences.password.first()
        return username == storedUsername && password == storedPassword
    }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        preferences.setLoggedIn(loggedIn)
    }

    val isLoggedIn: Flow<Boolean> = preferences.isLoggedIn
}