package com.example.myapplication.data.local

import androidx.compose.ui.geometry.Offset
import com.example.myapplication.R
import com.example.myapplication.data.Obra

object ProveedorObras {
    val obras = listOf(Obra(
        fotous = R.drawable.juanito_foto,
        usuario = "JuanitoArt",
        hora = "2h",
        titulo = "Pintura clasica",
        descripcion = "Tutorial para recrear una pintura famosa.",
        Tags = listOf("Pintura", "Tutorial"),
        foto = R.drawable.obra1,
        likes = "325",
        comentarios = "12",
        compartidos = "3",
        vistas = "1000",
        obraId  = 1
    ),
        Obra(
            fotous = R.drawable.maria_foto,
            usuario = "MariaCrea",
            hora = "2h",
            titulo = "Cuadro al lienzo",
            descripcion = "Tutorial para pintar un cuadro al óleo.",
            Tags = listOf("Oleo", "Tutorial"),
            foto = R.drawable.obra2,
            likes = "300k",
            comentarios = "120",
            compartidos = "3",
            vistas = "5000",
            obraId = 2
        ),
        Obra(
            fotous = R.drawable.juanito_foto,
            usuario = "JuanitoArt",
            hora = "2m",
            titulo = "Pintura abstracta",
            descripcion = "Explorando el arte abstracto con colores vibrantes.",
            Tags = listOf("Abstracto", "Colores"),
            foto = R.drawable.obra3,
            likes = "1500",
            comentarios = "45",
            compartidos = "3",
            vistas = "2000",
            obraId = 3
        )
        )

}