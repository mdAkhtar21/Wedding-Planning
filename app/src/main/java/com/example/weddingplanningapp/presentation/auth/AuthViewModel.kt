package com.example.weddingplanningapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weddingplanningapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _loginState = MutableStateFlow(false)
    val loginState: StateFlow<Boolean> = _loginState

    val isLoggedIn: StateFlow<Boolean> = MutableStateFlow(false)


    init {
        viewModelScope.launch {
            repository.isLoggedIn.collect {
                (isLoggedIn as MutableStateFlow).value = it
            }
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            repository.register(username, password)
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val success = repository.login(username, password)
            _loginState.value = success
            if(success) repository.setLoggedIn(true)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.setLoggedIn(false)
        }
    }

}