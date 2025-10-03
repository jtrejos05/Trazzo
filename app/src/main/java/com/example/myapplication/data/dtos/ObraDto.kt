package com.example.myapplication.data.dtos

import com.example.myapplication.data.Obra


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
        fotous = this.artista.fotousuario,
        usuario = this.artista.nombre,
        hora = this.createdAt,
        titulo = this.titulo,
        descripcion = this.descripcion,
        Tags = tagStrings as List<String>,
        foto = this.obraIMG,
        likes = likesString,
        comentarios = this.numComentarios.toString(),
        compartidos = this.compartidos.toString(),
        vistas = this.vistas.toString(),
        obraId = this.id.toString()
    )
}
