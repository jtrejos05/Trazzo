package com.example.myapplication.data.local

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.ui.graphics.Color
import com.example.myapplication.data.ActividadItem

object ProveedorActividad {
    val actividades = listOf(
        ActividadItem(
            icon = Icons.Filled.Home,
            descripcion = "Subió una nueva foto",
            tiempo = "Hace 2 horas",
            color = Color(0xFFBB86FC)
        ),
        ActividadItem(
            icon = Icons.Filled.Whatshot,
            descripcion = "Te ha gustado tu foto",
            tiempo = "Hace 1 hora",
            color = Color(0xFF03DAC5)
        ),
        ActividadItem(
            icon = Icons.Filled.ContentCut,
            descripcion = "Ha comentado en tu foto",
            tiempo = "Hace 30 minutos",
            color = Color(0xFFFFC107)
        ),
        ActividadItem(
            icon = Icons.Filled.Checkroom,
            descripcion = "Te ha seguido",
            tiempo = "Hace 10 minutos",
            color = Color(0xFFFF5722)
        )
    )
}