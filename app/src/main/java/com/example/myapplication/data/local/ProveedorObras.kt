package com.example.myapplication.data.local

import androidx.compose.ui.geometry.Offset
import com.example.myapplication.R
import com.example.myapplication.data.Obra

// Este objeto contiene una lista de obras de prueba para las publicaciones
object ProveedorObras {
    val obras = listOf(
        Obra(
            fotous = "https://cdn.pixabay.com/photo/2022/12/24/21/14/portrait-7676482_640.jpg",
            usuario = "JuanitoArt",
            hora = "2h",
            titulo = "Pintura clasica",
            descripcion = "Tutorial para recrear una pintura famosa.",
            Tags = listOf("Pintura", "Tutorial"),
            foto = "https://cdn.pixabay.com/photo/2019/03/02/16/26/man-4030112_640.jpg",
            likes = "325",
            comentarios = "12",
            compartidos = "3",
            vistas = "1000",
            obraId  = 1
    ),
        Obra(
            fotous = "https://cdn.pixabay.com/photo/2015/01/06/16/14/woman-590490_640.jpg",
            usuario = "MariaCrea",
            hora = "2h",
            titulo = "Cuadro al lienzo",
            descripcion = "Tutorial para pintar un cuadro al óleo.",
            Tags = listOf("Oleo", "Tutorial"),
            foto = "https://cdn.pixabay.com/photo/2016/02/17/09/20/watches-1204696_640.jpg",
            likes = "300k",
            comentarios = "120",
            compartidos = "3",
            vistas = "5000",
            obraId = 2
        ),
        Obra(
            fotous = "https://cdn.pixabay.com/photo/2022/12/24/21/14/portrait-7676482_640.jpg",
            usuario = "JuanitoArt",
            hora = "2m",
            titulo = "Pintura abstracta",
            descripcion = "Explorando el arte abstracto con colores vibrantes.",
            Tags = listOf("Abstracto", "Colores"),
            foto = "https://cdn.pixabay.com/photo/2017/06/03/20/12/art-2369664_640.jpg",
            likes = "1500",
            comentarios = "45",
            compartidos = "3",
            vistas = "2000",
            obraId = 3
        )
        )

}