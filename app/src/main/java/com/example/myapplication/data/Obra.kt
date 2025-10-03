package com.example.myapplication.data

import com.example.myapplication.data.dtos.ArtistaObraDto

data class Obra(
    val id: String,
    val artistaId: String,
    val titulo: String,
    val descripcion: String,
    val obraIMG: String,
    val numComentarios: String,
    val likes: String,
    val compartidos: String,
    val vistas: String,
    val createdAt: String,
    val updatedAt: String,
    val artista: ArtistaObraDto,
    val tags: String

)
