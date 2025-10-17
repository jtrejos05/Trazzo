package com.example.myapplication.data.dtos

import android.os.Build
import android.text.format.DateUtils
import com.google.firebase.Timestamp
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myapplication.data.Comentario
import com.example.myapplication.data.dtos.TimeHumanizer.tiempoVisual
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

data class ArtistaComentarioDto(
    val nombre: String,
    val fotousuario: String? = ""
){
    constructor(): this("","")
}

data class ObraComentarioDto(
    val titulo: String
){
    constructor(): this("")
}

data class ComentarioDto(
    val id: String?,
    val obraId: String,
    val artistaId: String,
    val parentComentarioId: Int?,
    val comentario: String,
    val numLikes: Int,
    val calificacion: Double,
    val createdAt: String,
    val updatedAt: String,
    val artista: ArtistaComentarioDto,
    val obra: ObraComentarioDto,
    var liked: Boolean=false
){
    constructor(): this("","","",0,"",0,0.0,"","", ArtistaComentarioDto("",""), ObraComentarioDto(""))
}



object TimeHumanizer {

    // Patrones soportados:
    // 1) 2025-10-14 11:52:04.041 -05:00
    // 2) 2025-10-14 11:52:04.041 -0500
    // 3) 2025-10-14T11:52:04.041Z
    // 4) 2025-10-14T11:52:04.041-05:00
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatters = listOf(
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS XXX", Locale.US),
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS XX",  Locale.US),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX",  Locale.US),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US) // literal Z
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseStringToMillis(s: String): Long? {
        val clean = s.trim().replace(Regex("\\s+"), " ")
        for (fmt in formatters) {
            try {
                return OffsetDateTime.parse(clean, fmt).toInstant().toEpochMilli()
            } catch (_: Throwable) { /* try next */ }
        }
        return null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun tiempoVisual(createdAt: Any?): String {
        val millis: Long = when (createdAt) {
            is Timestamp -> createdAt.toDate().time
            is Long      -> createdAt
            is String    -> parseStringToMillis(createdAt)
                ?: return createdAt // si falla parseo, devuelve el original
            else         -> return "—"
        }
        return DateUtils.getRelativeTimeSpanString(
            millis,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE
        ).toString()
    }
}
fun ComentarioDto.toComentario(): Comentario {
    var likesString = ""
    if (numLikes>1000) {
        val likesInK = numLikes / 1000.0
        likesString = String.format("%.1fk", likesInK)
    } else {
        likesString = numLikes.toString()
    }
    return Comentario(
        id = this.id ?: "",
        fotous = this.artista.fotousuario ?: "",
        usuario = this.artista.nombre, 
        hora = tiempoVisual(this.createdAt),
        comentario = this.comentario,
        likes = likesString,
        calificacion = this.calificacion.toDouble(),
        obraId = this.obraId,
        liked = this.liked

    )
}
