package com.example.myapplication.data.local

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Whatshot
import com.example.myapplication.data.BotonDeNavegacion

object ProveedorBotonesDeNavegacion {
    val botones = listOf(
        BotonDeNavegacion(
            text = "Inicio",
            icon = Icons.Filled.Home,
            selected = true
        ),
        BotonDeNavegacion(
            text = "Trending",
            icon = Icons.Filled.Whatshot,
            selected = false
        ),
        BotonDeNavegacion(
            text = "Origami",
            icon = Icons.Filled.ContentCut,
            selected = false
        ),
        BotonDeNavegacion(
            text = "Moda",
            icon = Icons.Filled.Checkroom,
            selected = false
        )
    )
}