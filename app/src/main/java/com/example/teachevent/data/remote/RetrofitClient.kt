package com.example.teachevent.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://gist.githubusercontent.com/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    // OkHttpClient con límites de tiempo de 5 segundos.
    // Si la conexión de red tarda más de esto, la app no se quedará congelada, sino que
    // cancela la espera y carga los eventos en caché de Room.
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Inyectamos el cliente configurado
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }
}