package com.example.myapplication.data.dtos

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



data class CreateObraDto(
    var id: String?,
    val artistaId: String,
    val obraIMG: String,
    val titulo: String,
    val descripcion: String,
    val Tags: List<String>,
    val createdAt: String,
    val updatedAt: String,
    val artista: ArtistaObraDto,
)
