package com.example.myapplication.data.local

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Update
import com.example.myapplication.data.NotificacionItem

object ProveedorNotificaciones {
    val notificaciones = listOf(
        NotificacionItem(
            mensaje = "Nuevo comentario en tu publicación",
            tiempo = "Hace 2 horas",
            icon = Icons.AutoMirrored.Filled.Comment
        ),
        NotificacionItem(
            mensaje = "Tu publicación ha sido compartida",
            tiempo = "Hace 1 día",
            icon = Icons.Filled.Share
        ),
        NotificacionItem(
            mensaje = "Nuevo seguidor: Juan Pérez",
            tiempo = "Hace 3 días",
            icon = Icons.Filled.PersonAdd
        ),
        NotificacionItem(
            mensaje = "Actualización de la aplicación disponible",
            tiempo = "Hace 5 días",
            icon = Icons.Filled.Update
        )
    )
}