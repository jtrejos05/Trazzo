package com.example.myapplication.data.dtos

import com.example.myapplication.data.Obra
import kotlin.Int


data class ArtistaObraDto(
    val nombre: String,
    val fotousuario: String
)

data class TagDto(
    val nombre: String
)

data class ObraDto(
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

fun ObraDto.toObra(): Obra {
    val tagStrings: MutableList<String?> = ArrayList<String?>()
    this.tags.forEach { tagDto -> {
        tagStrings.add(tagDto.nombre)
    } }
    var likesString = ""
    if (likes>1000) {
        val likesInK = likes / 1000.0
        likesString = String.format("%.1fk", likesInK)
    } else {
        likesString = likes.toString()
    }
    return Obra(

        id = id.toString(),
        createdAt = createdAt,
        titulo = titulo,
        descripcion = descripcion,
        obraIMG = obraIMG,
        likes = likesString,
        numComentarios = numComentarios.toString(),
        compartidos = compartidos.toString(),
        vistas = vistas.toString(),
        tags =tags.toString(),
        artista = artista,
        updatedAt = updatedAt,
        artistaId = artistaId.toString()

    )
}
