package com.example.myapplication.data.local

import com.example.myapplication.R
import com.example.myapplication.data.Comentario

// Este objeto contiene una lista de comentarios de prueba para las publicaciones
object ProveedorComentarios {
    val comentarios = listOf(
        Comentario(
            id = "1",
            fotous = "https://cdn.pixabay.com/photo/2025/09/10/11/25/duck-9826181_640.jpg",
            usuario = "Juanito Perez",
            hora = "5h",
            comentario = "Los pliegues estan explicados con mucha claridad",
            likes = 5,
            calificacion = 5.0
        ),
        Comentario(
            id = "2",
            fotous = "https://randomuser.me/api/portraits",
            usuario = "Maria Sato",
            hora = "2h",
            comentario = "¡Increible tutorial! Por fin logre hacer mi primera grulla perfecta. Los pliegues estan explicados super claramente",
            likes = 12,
            calificacion = 4.5
        )
    )
}