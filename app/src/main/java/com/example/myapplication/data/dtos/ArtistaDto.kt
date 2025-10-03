package com.example.myapplication.data.dtos

import com.example.myapplication.data.Artista
import com.example.myapplication.data.Comentario
import com.example.myapplication.data.Obra


data class ObrasArtistaDto(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val obraIMG: String,
    val numComentarios: Int,
    val likes: Int,
    val compartidos: Int,
    val vistas: Int,
    val createdAt: String,
    val updatedAt: String? = null
)

data class InteresesDto(
    val interes:String
)
data class ArtistaDto(
    val id: Int,
    val nombre: String,
    val correo: String,
    val fotousuario: String,
    val contraseña: String,
    val edad: Int,
    val profesion: String,
    val biografia: String,
    val seguidores: Int,
    val seguidos: Int,
    val likes: Int,
    val createdAt: String,
    val updatedAt: String,
    val obras: List<ObrasArtistaDto>,
    val intereses: List<InteresesDto>
)
fun ObrasArtistaDto.toObra(fotousuario: String, usuario: String): Obra{
    var likesString = ""
    if (likes>1000) {
        val likesInK = likes / 1000.0
        likesString = String.format("%.1fk", likesInK)
    } else {
        likesString = likes.toString()
    }
    return Obra(
        fotous = fotousuario,
        usuario = usuario,
        hora = this.createdAt,
        titulo = this.titulo,
        descripcion = this.descripcion,
        foto = this.obraIMG,
        likes = likesString,
        comentarios = this.numComentarios.toString(),
        compartidos = this.compartidos.toString(),
        vistas = this.vistas.toString(),
        obraId = this.id.toString(),
        Tags = emptyList()
    )
}
fun ArtistaDto.toArtista(): Artista {
    var obras = this.obras.map { it.toObra(this.fotousuario, this.nombre) }
    var intereses = this.intereses.map { it.interes }


    return Artista(
        id=this.id.toString(),
        img = this.fotousuario,
        correo = this.correo,
        contrasena = this.contraseña,
        usuario = this.nombre,
        edad = this.edad,
        profesion = this.profesion,
        biografia = this.biografia,
        seguidores = this.seguidores,
        siguiendo = this.seguidos,
        likes = likes,
        obras=  obras,
        interses = intereses


    )
}
