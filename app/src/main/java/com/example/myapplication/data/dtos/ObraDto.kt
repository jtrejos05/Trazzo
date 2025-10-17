package com.example.myapplication.data.dtos

import android.os.Build
import android.text.format.DateUtils
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myapplication.data.Obra
import com.example.myapplication.data.dtos.TimeHumanizer.tiempoVisual
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


data class ArtistaObraDto(
    val nombre: String,
    val fotousuario: String
){
    constructor(): this("","")
}

data class TagDto(
    val tag: String
){
    constructor(): this("")
}

data class ObraDto(
    val id: String,
    val artistaId: String,
    val titulo: String,
    val descripcion: String,
    val obraIMG: String,
    val numComentarios: Int,
    val numLikes: Int,
    val compartidos: Int,
    val vistas: Int,
    val createdAt: String,
    val updatedAt: String,
    val artista: ArtistaObraDto,
    val tags: List<TagDto>,
    var liked: Boolean = false
){
    constructor(): this("","","","","",0,0,0,0,"","", ArtistaObraDto("",""),emptyList())
}

@RequiresApi(Build.VERSION_CODES.O)
fun ObraDto.toObra(): Obra {
    val tagStrings = tags.map { it.tag }
    var likesString = ""
    if (numLikes>1000) {
        val likesInK = numLikes / 1000.0
        likesString = String.format("%.1fk", likesInK)
    } else {
        likesString = numLikes.toString()
    }
    Log.d("Obras 3", tiempoVisual(this.updatedAt))
    return Obra(
        fotous = this.artista.fotousuario,
        usuario = this.artista.nombre,
        hora = tiempoVisual(this.updatedAt),
        titulo = this.titulo,
        descripcion = this.descripcion,
        Tags = tagStrings,
        foto = this.obraIMG,
        likes = likesString,
        comentarios = this.numComentarios.toString(),
        compartidos = this.compartidos.toString(),
        vistas = this.vistas.toString(),
        obraId = this.id.toString(),
        artistaId = this.artistaId.toString(),
        liked = this.liked
    )
}
