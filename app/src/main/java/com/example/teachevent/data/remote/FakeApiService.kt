package com.example.teachevent.data.remote

class FakeApiService : ApiService {
    override suspend fun fetchEvents(): List<EventDto> {
        return listOf(
            EventDto(
                id = "1",
                title = "Congreso UMA 2024",
                description = "Evento académico de la Universidad Modular Abierta.",
                date = "15 de Junio, 2024",
                location = "Campus Central UMA",
                imageUrl = "https://via.placeholder.com/150",
                hasAvailableSlots = true,
                speakers = listOf(
                    SpeakerDto("Dr. Morales", "Decano", "UMA")
                ),
                agenda = listOf(
                    AgendaItemDto("08:00 AM", "Inauguración")
                )
            )
        )
    }
}
