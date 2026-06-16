package com.example.teachevent.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.teachevent.domain.model.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    event: Event?,
    onBackClick: () -> Unit
) {
    if (event == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Selecciona un evento para ver los detalles")
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del Evento", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Imagen del Banner
            item {
                AsyncImage(
                    model = event.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Información Principal
            item {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        Text(text = event.date, modifier = Modifier.padding(start = 8.dp), style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(modifier = Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        Text(text = event.location, modifier = Modifier.padding(start = 8.dp), style = MaterialTheme.typography.bodyMedium)
                    }

                    // Badge de Cupos
                    val statusText = if (event.hasAvailableSlots) "CUPOS DISPONIBLES" else "AGOTADO"
                    val statusColor = if (event.hasAvailableSlots) Color(0xFF2E7D32) else Color(0xFFC62828)
                    
                    Surface(
                        modifier = Modifier.padding(top = 16.dp),
                        color = statusColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = statusText,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = statusColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))

                    Text(
                        text = "Acerca de este evento",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = event.description,
                        modifier = Modifier.padding(top = 8.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp
                    )
                }
            }

            // Expositores (Speakers)
            if (event.speakers.isNotEmpty()) {
                item {
                    Text(
                        text = "Expositores",
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(event.speakers) { speaker ->
                    ListItem(
                        headlineContent = { Text(speaker.name, fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("${speaker.role} • ${speaker.company}") },
                        leadingContent = {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(speaker.name.take(1), color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }
                        }
                    )
                }
            }

            // Agenda
            if (event.agenda.isNotEmpty()) {
                item {
                    Text(
                        text = "Agenda",
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(event.agenda) { item ->
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = item.time,
                            modifier = Modifier.width(80.dp),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(text = item.title)
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}
