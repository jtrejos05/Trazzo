package com.example.myapplication.data.local

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Portrait
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Hd
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Portrait
import androidx.compose.material.icons.outlined.Whatshot
import com.example.myapplication.data.BotonDeNavegacion
import com.example.myapplication.navigation.Rutas

object ProveedorBotonesDeNavegacion {
    val botones = listOf(
        BotonDeNavegacion(
            icon = Icons.Filled.Home,
            icon2 = Icons.Outlined.Home,
            route = Rutas.Principal.ruta
        ),
        BotonDeNavegacion(
            icon = Icons.Filled.Add,
            icon2 = Icons.Outlined.Add,
            route = Rutas.Subir.ruta
        ),
        BotonDeNavegacion(
            icon = Icons.Filled.Whatshot,
            icon2 = Icons.Outlined.Whatshot ,
            route = Rutas.Trending.ruta
        ),
        BotonDeNavegacion(
            icon = Icons.Filled.Portrait,
            icon2 = Icons.Outlined.Portrait,
            route = Rutas.Perfil.ruta
        )
    )
}