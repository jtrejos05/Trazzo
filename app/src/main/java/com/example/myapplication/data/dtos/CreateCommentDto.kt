package com.example.myapplication.data.dtos

data class CreateCommentDto(
    val calificacion: Double,
    val comentario: String,
    val artistaId: Int,
    val obraId: Int,
    val parentComentarioId: Int?,
    val id: Int?
)
