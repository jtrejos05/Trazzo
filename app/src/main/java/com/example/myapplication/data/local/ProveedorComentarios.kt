package com.example.myapplication.data.local

import com.example.myapplication.R
import com.example.myapplication.data.Comentario

object ProveedorComentarios {
    val comentarios = listOf(
        Comentario(
            fotous = R.drawable.juanito_foto,
            usuario = "Juanito Perez",
            hora = "5h",
            comentario = "Los pliegues estan explicados con mucha claridad",
            likes = 5,
        ),
        Comentario(
            fotous = R.drawable.maria_foto,
            usuario = "Maria Sato",
            hora = "2h",
            comentario = "¡Increible tutorial! Por fin logre hacer mi primera grulla perfecta. Los pliegues estan explicados super claramente",
            likes = 12,
        )
    )
}