package com.example.myapplication.data.dtos

import android.text.format.DateUtils
import com.example.myapplication.data.Comentario
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

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
    val likes: Int,
    val calificacion: Double,
    val createdAt: String,
    val updatedAt: String,
    val artista: ArtistaComentarioDto,
    val obra: ObraComentarioDto
)

fun tiempoVisual(createdAt: String): String {

    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        val date = sdf.parse(createdAt) ?: return createdAt
        val timeInMillis = date.time

        DateUtils.getRelativeTimeSpanString(
            timeInMillis,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE
        ).toString()
    } catch (e: Exception) {
        createdAt
    }
}
fun ComentarioDto.toComentario(): Comentario {
    return Comentario(
        id = this.id.toString(),
        fotous = this.artista.fotousuario,
        usuario = this.artista.nombre, 
        hora = tiempoVisual(this.createdAt),
        comentario = this.comentario,
        likes = this.likes,
        calificacion = this.calificacion.toDouble(),
        obraId = this.obraId.toString()

    )
}
