package com.nocuntry.c1322ftkotlin.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class Notification(val id: Int, val message: String)

@Composable
fun NotificationsScreen() {
    var notifications by remember { mutableStateOf(getDummyNotifications()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Notifications",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(notifications) { notification ->
                NotificationItem(notification = notification)
                Divider()
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Acción al hacer clic en la notificación */ }
            .clip(MaterialTheme.shapes.medium)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = notification.message,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Hace un momento", // Puedes mostrar la hora real aquí
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
        }
    }
}

fun getDummyNotifications(): List<Notification> {
    // Simula notificaciones
    return listOf(
        Notification(1, "¡Nueva actualización disponible!"),
        Notification(2, "Tienes 3 mensajes nuevos."),
        Notification(3, "Evento próximo: Historia exploración a marte a las 2 PM."),
        // Agrega más notificaciones aquí
    )
}
