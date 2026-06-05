//creado por Edwin Mauricio Morales Rodriguez
package com.example.teachevent.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import com.example.techevent.domain.model.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    event: Event?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Compartir", tint = Color.White)
                    }
                    IconButton(onClick = {}) {
                        Text("❤️", style = MaterialTheme.typography.titleMedium) // Botón de favoritos superior
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        if (event == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Selecciona un evento para ver su detalle.")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color(0xFFF8FAFC)) // Fondo claro premium
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(Color(0xFF0F172A))
                ) {
                    AsyncImage(
                        model = event.imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        alpha = 0.4f
                    )


                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xFF0F172A)),
                                    startY = 100f
                                )
                            )
                    )


                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Surface(
                            color = Color(0xFF6200EE),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "CONGRESO INTERNACIONAL",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = event.title,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-20).dp), // Efecto flotante sobre el banner oscuro
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {


                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "📅 ${event.date}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "📍 ${event.location}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        }

                        val statusText = if (event.hasAvailableSlots) "Cupos Disponibles" else "Agotado"
                        val statusColor = if (event.hasAvailableSlots) Color(0xFF2E7D32) else Color(0xFFC62828)
                        Text(
                            text = statusText,
                            style = MaterialTheme.typography.labelLarge,
                            color = statusColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFE2E8F0))


                        Text(text = "Descripción", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = event.description, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF475569))

                        Spacer(modifier = Modifier.height(24.dp))


                        if (event.speakers.isNotEmpty()) {
                            Text(text = "Ponentes", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                            Spacer(modifier = Modifier.height(12.dp))

                            event.speakers.forEach { speaker ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Foto redonda del ponente usando Coil (Unidad 5)
                                    AsyncImage(
                                        model = "https://i.pravatar.cc/150?u=${speaker.name}", // Avatar simulado pro basado en su nombre
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )

                                    Column(modifier = Modifier.padding(start = 12.dp)) {
                                        Text(text = speaker.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                                        Text(text = "${speaker.role} — ${speaker.company}", style = MaterialTheme.typography.bodySmall, color = Color(0xFF64748B))
                                    }
                                }
                            }
                            Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFE2E8F0))
                        }


                        if (event.agenda.isNotEmpty()) {
                            Text(text = "Agenda", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                            Spacer(modifier = Modifier.height(12.dp))

                            event.agenda.forEach { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = item.time,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF6200EE), // Hora en color morado
                                        modifier = Modifier.width(65.dp)
                                    )
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = item.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = Color(0xFF1E293B))
                                    }
                                }
                            }

                            TextButton(
                                onClick = {},
                                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 8.dp)
                            ) {
                                Text("Ver agenda completa", color = Color(0xFF6200EE), fontWeight = FontWeight.Bold)
                            }

                            Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFE2E8F0))
                        }


                        Text(text = "Lugar", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                        Spacer(modifier = Modifier.height(8.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth().height(140.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize().padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("🗺️", style = MaterialTheme.typography.headlineLarge)
                                Column(modifier = Modifier.padding(start = 12.dp)) {
                                    Text(text = "Centro de Convenciones", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                    Text(text = "Av. Tecnológica, San Salvador, El Salvador", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}