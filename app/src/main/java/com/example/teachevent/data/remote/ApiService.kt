//creado por Edwin Mauricio Morales Rodriguez
package com.example.teachevent.data.remote

import retrofit2.http.GET

interface ApiService {
    @GET("https://gist.githubusercontent.com/Eledwinin/d00bfaa8eb27d5a502b5a0daab50a13e/raw/events.json")
    suspend fun fetchEvents(): List<EventDto>
}
