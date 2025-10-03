package com.example.myapplication.data

import androidx.annotation.DrawableRes
import com.example.myapplication.data.dtos.ArtistaObraDto
import com.example.myapplication.data.dtos.TagDto

data class Obra(
    val id: Int,
    val artistaId: Int,
    val titulo: String,
    val descripcion: String,
    val obraIMG: String,
    val numComentarios: Int,
    val likes: Int,
    val compartidos: Int,
    val vistas: Int,
    val createdAt: String,
    val updatedAt: String,
    val artista: ArtistaObraDto,
    val tags: List<TagDto>
)
