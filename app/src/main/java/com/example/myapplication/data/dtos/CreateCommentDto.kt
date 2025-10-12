package com.example.myapplication.data.dtos

data class CreateCommentUserDto(
    val nombre: String,
    val fotousuario: String? = ""
)
data class CreateCommentDto(
    val calificacion: Double,
    val comentario: String,
    val artistaId: String,
    val obraId: String,
    val parentComentarioId: Int?,
    var id: String?,
    var artista: CreateCommentUserDto? = null
)
