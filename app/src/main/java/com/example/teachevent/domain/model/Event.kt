package com.example.teachevent.domain.model

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val imageUrl: String,
    val hasAvailableSlots: Boolean,
    val speakers: List<Speaker> = emptyList(),
    val agenda: List<AgendaItem> = emptyList(),
    val isFavorite: Boolean = false
)
