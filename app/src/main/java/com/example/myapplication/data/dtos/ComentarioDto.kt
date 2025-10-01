package com.example.myapplication.data.dtos

import com.example.myapplication.data.Comentario

data class ArtistaComentarioDto(
    val nombre: String,
    val fotousuario: String
)

data class ObraComentarioDto(
    val titulo: String
)

data class ComentarioDto(
    val id: Int,
    val obraId: Int,
    val artistaId: Int,
    val parentComentarioId: Int?,
    val comentario: String,
    val hora: String,
    val likes: Int,
    val calificacion: Int,
    val createdAt: String,
    val updatedAt: String,
    val artista: ArtistaComentarioDto,
    val obra: ObraComentarioDto
)

fun ComentarioDto.toComentario(): Comentario {
    return Comentario(
        id = this.id.toString(),
        fotous = this.artista.fotousuario,
        usuario = this.artista.nombre, 
        hora = this.hora,
        comentario = this.comentario,
        likes = this.likes,
        calificacion = this.calificacion.toDouble()
    )
}
