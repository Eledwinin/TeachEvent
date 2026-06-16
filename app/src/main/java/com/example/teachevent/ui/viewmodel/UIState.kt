package com.example.teachevent.ui.viewmodel

import com.example.teachevent.domain.model.Event

sealed interface UIState {
    object Loading : UIState

    data class Success(
        val events: List<Event>,
        val isOfflineMode: Boolean = false
    ) : UIState

    data class Error(val message: String) : UIState
}
