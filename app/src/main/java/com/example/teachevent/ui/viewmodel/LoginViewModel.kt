package com.example.teachevent.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teachevent.data.local.SessionDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel(private val sessionDataStore: SessionDataStore) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error("Los campos no pueden estar vacíos")
            return
        }

        _uiState.value = LoginUiState.Loading

        if (username == "admin" && password == "1234") {
            viewModelScope.launch {
                sessionDataStore.saveSession(true)
                _uiState.value = LoginUiState.Success
            }
        } else {
            _uiState.value = LoginUiState.Error("Usuario o contraseña incorrectos")
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}